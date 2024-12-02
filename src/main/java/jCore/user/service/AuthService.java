package jCore.user.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jCore.user.model.dto.CustomUserDetails;
import jCore.user.model.dto.LoginRequest;
import jCore.user.model.dto.Token;
import jCore.user.model.entity.Auth;
import jCore.user.model.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import jCore.user.repository.AuthRepository;
import jCore.user.repository.TokenRepository;
import jCore.common.AES128Util;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.*;

@Service
@Transactional
@RequiredArgsConstructor
public class AuthService implements UserDetailsService {
    @Autowired AuthRepository authRepository;
    @Autowired
    TokenRepository tokenRepository;
    @Autowired private PasswordEncoder passwordEncoder;
    @Value("${jwt.secret.key}") private String salt;
    private Key secretKey;
    // 만료시간
    @Value("${token.access-expired-time}") private long ACCESS_EXPIRED_TIME;
    // 재발급 토큰 만료시간
    @Value("${token.refresh-expired-time}") private long REFRESH_EXPIRED_TIME;
    private final AES128Util aes128Util = new AES128Util();
    @PostConstruct
    protected void init() {
        secretKey = Keys.hmacShaKeyFor(salt.getBytes(StandardCharsets.UTF_8));
    }

    public String createAccessToken(String domainCd, String userCd, Set<Auth> roles) {
        Claims claims = Jwts.claims().setSubject(userCd);
        claims.put("domainCd", domainCd);
        claims.put("userCd", userCd);
        //claims.put("roles", roles);
        return Jwts.builder()
                .setClaims(claims)
                .setExpiration(new Date(System.currentTimeMillis()  + ACCESS_EXPIRED_TIME))
                .setIssuedAt(new Date())
                .signWith(secretKey, SignatureAlgorithm.HS256)
                .compact();
    }

    public String createRefreshToken(String domainCd, String userCd) {
        Claims claims = Jwts.claims();
        claims.put("domainCd", domainCd);
        claims.put("userCd", userCd);
        return Jwts.builder()
                .addClaims(claims)
                .setExpiration(new Date(System.currentTimeMillis() + REFRESH_EXPIRED_TIME))
                .setIssuedAt(new Date())
                .signWith(secretKey, SignatureAlgorithm.HS256)
                .compact();
    }

    public Map<String, Object> generateToken(LoginRequest loginRequest){
        Map<String, Object> resultMap = new LinkedHashMap<String, Object>();
        User userEntity = authRepository.findByUserId(loginRequest.getUserId()).orElseThrow(() ->
            new BadCredentialsException(loginRequest.getUserId()+" : 아이디가 존재하지 않습니다."));
        if (!passwordEncoder.matches(loginRequest.getUserPw(), userEntity.getUserPw())) {
            throw new BadCredentialsException("잘못된 비밀번호입니다.");
        }
        String domainCd = userEntity.getDomainCd();
        String userCd = userEntity.getUserCd();
        Set<Auth> roles = userEntity.getRoles();

        String accessToken = createAccessToken(domainCd, userCd, roles);
        String refreshToken = createRefreshToken(domainCd, userCd);
        Token token = new Token(accessToken, refreshToken);
        tokenRepository.save(token);

        resultMap.put("token", accessToken);
        return resultMap;
    }

    public Map<String, Object> refreshToken(HttpServletRequest request) {
        Map<String, Object> resultMap = new LinkedHashMap<String, Object>();
        request.getHeader("authorization");
        Token token = tokenRepository.findById(request.getHeader("authorization")).orElseThrow(() ->
            new BadCredentialsException("토큰이 존재하지 않습니다.")
        );
        String refreshToken = token.getRefreshToken();
        Claims claim = Jwts.parserBuilder().setSigningKey(secretKey).build()
                .parseClaimsJws(refreshToken).getBody();
        User userEntity = authRepository.findByUserCd(claim.get("userCd").toString()).orElseThrow(() ->
            new BadCredentialsException("유저가 존재하지 않습니다.")
        );

        String domainCd = userEntity.getDomainCd();
        String userCd = userEntity.getUserCd();
        Set<Auth> roles = userEntity.getRoles();

        String accessToken = createAccessToken(domainCd, userCd, roles);
        refreshToken = createRefreshToken(domainCd, userCd);
        tokenRepository.delete(token);
        token = new Token(accessToken, refreshToken);
        tokenRepository.save(token);

        resultMap.put("token", accessToken);
        return resultMap;
    }

    @Override
    public UserDetails loadUserByUsername(String userNm) throws UsernameNotFoundException {
        User userEntity = authRepository.findByUserNm(userNm).orElseThrow(
            () -> new UsernameNotFoundException("Invalid authentication!")
        );
        return new CustomUserDetails(userEntity);
    }
}