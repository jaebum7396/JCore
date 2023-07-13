package user.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import user.model.*;
import user.repository.UserInfoRepository;
import user.repository.UserProfileImageRepository;
import user.repository.UserRepository;
import user.utils.AES128Util;

import javax.servlet.http.HttpServletRequest;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.*;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService implements UserDetailsService {
    @Autowired UserRepository userRepository;
    @Autowired UserInfoRepository userInfoRepository;
    @Autowired UserProfileImageRepository userProfileImageRepository;
    @Autowired PasswordEncoder passwordEncoder;
    @Autowired private final AES128Util aes128Util = new AES128Util();
    private final RedisTemplate<String, Object> redisTemplate;
    @Value("${jwt.secret.key}")
    private String JWT_SECRET_KEY;

    public boolean duplicateIdValidate(SignupRequest signupRequest) {
        boolean check = userRepository.findByUserId(signupRequest.getUserId()).isPresent();
        return check;
    }
    @Override
    public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException {
        User userEntity = userRepository.findByUserNm(name).orElseThrow(
            () -> new UsernameNotFoundException("Invalid authentication!")
        );
        return new CustomUserDetails(userEntity);
    }

    public Claims getClaims(HttpServletRequest request){
        try{
            Key secretKey = Keys.hmacShaKeyFor(JWT_SECRET_KEY.getBytes(StandardCharsets.UTF_8));
            Claims claim = Jwts.parserBuilder().setSigningKey(secretKey).build()
                    .parseClaimsJws(request.getHeader("authorization")).getBody();
            return claim;
        } catch (ExpiredJwtException e) {
            throw new ExpiredJwtException(null, null, "로그인 시간이 만료되었습니다.");
        } catch (Exception e) {
            throw new BadCredentialsException("인증 정보에 문제가 있어 세션을 종료합니다.");
        }
    }

    public Map<String, Object> signup(SignupRequest signupRequest) throws Exception {
        System.out.println("UserService.signup.params : " + signupRequest.toString());
        Map<String, Object> resultMap = new LinkedHashMap<String, Object>();
        //중복 아이디 검사
        if (duplicateIdValidate(signupRequest)) {
            throw new BadCredentialsException("중복된 아이디입니다.");
        }
        signupRequest.setUserPw(passwordEncoder.encode(signupRequest.getUserPw()));
        User userEntity = signupRequest.toEntity();
        userEntity.setDeleteYn("N");
        //ROLE 설정
        Set<Auth> roles = new HashSet<>();
        roles.add(Auth.builder().authType("ROLE_USER").build());
        userEntity.setRoles(roles);

        userRepository.save(userEntity);

        UserInfo userInfo = UserInfo.builder()
                .userCd(userEntity.getUserCd())
                .userNickNm(userEntity.getUserNm())
                .userGender(signupRequest.getUserGender())
                .lookingForGender((signupRequest.getUserGender().equals("남자") ? "여자" : "남자"))
                .deleteYn("N")
                .build();

        String profileImgUrlCommon = "image/profile/common.png";
        System.out.println("userGender : "+signupRequest.getUserGender());
        if(signupRequest.getUserGender().equals("남자")){
            profileImgUrlCommon = "image/profile/man_common.png";
        }else if(signupRequest.getUserGender().equals("여자")){
            profileImgUrlCommon = "image/profile/woman_common.png";
        }

        UserProfileImage userProfileImageCommon =
            UserProfileImage.builder()
                .userCd(userEntity.getUserCd())
                .profileImgUrl(profileImgUrlCommon)
                .mainYn("Y")
                .defaultYn("Y")
                .deleteYn("N")
                .build();

        userInfo.addUserProfileImage(userProfileImageCommon);
        userEntity.setUserInfo(userInfo);

        userRepository.save(userEntity);

        //레디스를 통해 friendInfo 또한 저장 해준다
        System.out.println("redis 전송 userInfo: " + userInfo.toString());
        redisTemplate.convertAndSend("updateUserInfo", userInfo);
        resultMap.put("userInfo", userInfo);

        resultMap.put("userId", userEntity.getUserId());
        resultMap.put("userNm", userEntity.getUserNm());
        resultMap.put("roles", userEntity.getRoles());

        return resultMap;
    }

    public Map<String, Object> saveUserInfo(HttpServletRequest request, UserInfo updateUserInfo){
        System.out.println("UserService.saveUserInfo.params : " + updateUserInfo.toString());
        Map<String, Object> resultMap = new LinkedHashMap<String, Object>();

        Claims claim = getClaims(request);
        String userCd = claim.get("userCd", String.class);
        UserInfo userInfo = userInfoRepository.findByUserCd(userCd).orElseGet(() -> {
            return UserInfo.builder()
                .userCd(userCd)
                .build();
        });
        // 업데이트할 필드가 있다면 업데이트합니다.
        if (updateUserInfo.getUserNickNm() != null) {
            userInfo.setUserNickNm(updateUserInfo.getUserNickNm());
        }
        if (updateUserInfo.getAboutMe() != null) {
            userInfo.setAboutMe(updateUserInfo.getAboutMe());
        }
        if (updateUserInfo.getUserCharacter() != null) {
            userInfo.setUserCharacter(updateUserInfo.getUserCharacter());
        }
        if (updateUserInfo.getLookingForGender() != null) {
            userInfo.setLookingForGender(updateUserInfo.getLookingForGender());
        }
        if (updateUserInfo.getUserProfileImages().size() != 0) {
            if("Y".equals(updateUserInfo.getUserProfileImages().get(0).getDeleteYn())){
                System.out.println("프로필 이미지 삭제 프로세스");
                for(UserProfileImage upi : updateUserInfo.getUserProfileImages()){
                    System.out.println("삭제할 이미지 객체 : "+upi.toString());
                    //upi.setUserInfo(userInfo);
                    //userProfileImageRepository.save(upi);
                    for(UserProfileImage upi2 : userInfo.getUserProfileImages()){
                        System.out.println("삭제할 이미지 객체 : "+upi.getUserProfileImageCd()+" / "+upi2.getUserProfileImageCd());
                        if(upi2.getUserProfileImageCd().equals(upi.getUserProfileImageCd())){
                            upi2.setDeleteYn("Y");
                            upi2.setDefaultYn("N");
                            upi2.setMainYn("N");
                        }
                    }
                }
            }else{
                for(UserProfileImage upi: userInfo.getUserProfileImages()){
                    upi.setMainYn("N");
                }
                for(UserProfileImage upi : updateUserInfo.getUserProfileImages()){
                    //upi.setUserInfo(userInfo);
                    //userProfileImageRepository.save(upi);
                    upi.setUserCd(userCd);
                    upi.setDeleteYn("N");
                    upi.setDefaultYn("N");
                    upi.setMainYn("Y");
                    userInfo.addUserProfileImage(upi);
                }
            }
        }
        userInfo = userInfoRepository.save(userInfo);

        Pageable page = Pageable.ofSize(10);
        //Page<User> usersPage = userRepository.findUsersWithPageable(userInfo.getUserCd(), page);
        //System.out.println("usersPage = " + usersPage.getContent().toString());

        System.out.println("redis 전송 userInfo: " + userInfo.toString());
        redisTemplate.convertAndSend("updateUserInfo", userInfo);
        resultMap.put("userInfo", userInfo);

        return resultMap;
    }

    public Map<String, Object> getMyInfo(HttpServletRequest request){
        Map<String, Object> resultMap = new LinkedHashMap<String, Object>();

        Claims claim = getClaims(request);
        String userCd = claim.get("userCd", String.class);
        User userEntity = userRepository.getMyInfo(userCd).get();
        resultMap.put("user", userEntity);

        return resultMap;
    }

    public Map<String, Object> getUsersWithPageable(HttpServletRequest request, String queryString, Pageable page) {
        Response response;
        Map<String, Object> resultMap = new LinkedHashMap<String, Object>();
        HashMap<String, Object> paramMap = new HashMap<String, Object>();
        List<User> userArr = new ArrayList<User>();

        Claims claim = getClaims(request);
        String userCd = claim.get("userCd", String.class);

        Page<User> usersPage = userRepository.findUsersWithPageable(queryString, page);
        userArr = usersPage.getContent();

        resultMap.put("userArr", userArr);
        resultMap.put("p_page", page.getPageNumber());

        return resultMap;
    }

    public HashMap<String, Object> userGrid(HttpServletRequest request, HashMap<String, Object> mapParam) {
        Claims claim = getClaims(request);
        String userCd = claim.get("userCd", String.class);

        HashMap<String, Object> resultMap = new HashMap<String, Object>();
        HashMap<String, Object> dataMap = new HashMap<String, Object>();
        HashMap<String, Object> paginationMap = new HashMap<String, Object>();
        List<User> userArr = new ArrayList<User>();

        int page = mapParam.get("page").toString().isEmpty() ? 0 : Integer.parseInt(mapParam.get("page").toString());
        int perPage = mapParam.get("perPage").toString().isEmpty() ? 10 : Integer.parseInt(mapParam.get("perPage").toString());
        int offset = (page-1) * perPage;

        System.out.println("page : "+ page + " ,perPage : " + perPage+ " ,offset : " + offset);
        Pageable pageable = PageRequest.of(page-1, perPage);
        mapParam.put("offset", offset);
        Page<User> usersPage = userRepository.userGrid(mapParam, pageable);

        paginationMap.put("page", page);
        paginationMap.put("totalCount", usersPage.getTotalElements());
        System.out.println("usersPage.getTotalElements() : " + usersPage.getTotalElements());

        userArr = usersPage.getContent();

        mapParam.put("offset", offset);
        mapParam.put("delYn", 'N');

        resultMap.put("result", "OK");
        resultMap.put("result", true);

        dataMap.put("contents", userArr);
        dataMap.put("pagination", paginationMap);
        resultMap.put("data", dataMap);

        return resultMap;
    }
}