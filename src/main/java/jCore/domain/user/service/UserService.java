package jCore.domain.user.service;

import io.jsonwebtoken.Claims;
import jCore.domain.user.model.dto.request.SaveUserRequest;
import jCore.domain.user.repository.UserInfoRepository;
import jCore.domain.user.repository.UserProfileImageRepository;
import jCore.domain.user.repository.UserRepository;
import jCore.domain.user.model.dto.SignupRequest;
import jCore.domain.auth.model.entity.Auth;
import jCore.domain.user.model.entity.User;
import jCore.domain.user.model.entity.UserInfo;
import jCore.domain.user.model.entity.UserProfileImage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import jCore.common.CommonUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class UserService {
    @Autowired CommonUtils commonUtils;
    @Autowired UserRepository userRepository;
    @Autowired UserInfoRepository userInfoRepository;
    @Autowired UserProfileImageRepository userProfileImageRepository;
    @Autowired PasswordEncoder passwordEncoder;
    private final RedisTemplate<String, Object> redisTemplate;
    @Value("${jwt.secret.key}")
    private String JWT_SECRET_KEY;

    public Map<String, Object> saveUser(SaveUserRequest saveUserRequest) throws Exception {
        log.info("UserService.saveUser.params : " + saveUserRequest.toString());
        Map<String, Object> resultMap = new LinkedHashMap<String, Object>();

        // 2. 비밀번호 암호화
        String encodedPassword = passwordEncoder.encode(saveUserRequest.getUserPw());

        // 3. User 생성
        User user = saveUserRequest.toEntity();
        user.setUserPw(encodedPassword);
        user = userRepository.save(user);

        String userCd = user.getUserCd();

        // 4. UserInfo 생성
        Optional<UserInfo> userInfoOpt = userInfoRepository.findByUser(user);
        userInfoOpt.ifPresent(userInfo -> {
            log.info("Updating UserInfo" + userInfo);  // UserInfo 업데이트 시점 확인
        });
        UserInfo userInfo = userInfoOpt.orElseGet(() -> {
            log.info("Creating new UserInfo");  // 새로운 UserInfo 생성 시점 확인
            return UserInfo.builder().build();
        });
        userInfo.setUserNm(saveUserRequest.getUserNm());
        userInfo.setUserPhoneNo(saveUserRequest.getUserPhoneNo());
        userInfo.setUserGender(saveUserRequest.getUserGender());
        userInfo.setUser(user);
        userInfo = userInfoRepository.save(userInfo);

        user.setUserInfo(userInfo);
        user = userRepository.save(user);

        resultMap.put("userCd", userCd);
        return resultMap;
    }

    public Map<String, Object> getMyInfo(HttpServletRequest request){
        Map<String, Object> resultMap = new LinkedHashMap<String, Object>();

        Claims claim = commonUtils.getClaims(request);
        String userId = claim.getSubject();
        User userEntity = userRepository.getMyInfo(userId).get();
        resultMap.put("user", userEntity);

        return resultMap;
    }

    public Map<String, Object> getUsersWithPageable(HttpServletRequest request, String queryString, Pageable page) {
        Map<String, Object> resultMap = new LinkedHashMap<String, Object>();
        HashMap<String, Object> paramMap = new HashMap<String, Object>();
        List<User> userArr = new ArrayList<User>();

        Claims claim = commonUtils.getClaims(request);
        String userCd = claim.get("userCd", String.class);

        Page<User> usersPage = userRepository.findUsersWithPageable(queryString, page);
        userArr = usersPage.getContent();

        resultMap.put("userArr", userArr);
        resultMap.put("p_page", page.getPageNumber());

        return resultMap;
    }

    public HashMap<String, Object> userGrid(HttpServletRequest request, HashMap<String, Object> mapParam) {
        Claims claim = commonUtils.getClaims(request);
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