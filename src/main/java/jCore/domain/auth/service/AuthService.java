package jCore.domain.auth.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jCore.domain.auth.model.entity.Auth;
import jCore.domain.auth.repository.AuthRepository;
import jCore.domain.token.dto.Token;
import jCore.domain.token.repository.TokenRepository;
import jCore.domain.user.model.dto.LoginRequest;
import jCore.domain.user.model.entity.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import jCore.common.AES128Util;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.*;

@Service
@Transactional
public class AuthService {
    private final AuthRepository authRepository;
    private final TokenRepository tokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final AES128Util aes128Util;

    @Value("${jwt.secret.key}")
    private String salt;

    @Value("${token.access-expired-time}")
    private long ACCESS_EXPIRED_TIME;

    @Value("${token.refresh-expired-time}")
    private long REFRESH_EXPIRED_TIME;

    private Key secretKey;

    public AuthService(
            AuthRepository authRepository,
            TokenRepository tokenRepository,
            PasswordEncoder passwordEncoder) {
        this.authRepository = authRepository;
        this.tokenRepository = tokenRepository;
        this.passwordEncoder = passwordEncoder;
        this.aes128Util = new AES128Util();
    }

    @PostConstruct
    protected void init() {
        secretKey = Keys.hmacShaKeyFor(salt.getBytes(StandardCharsets.UTF_8));
    }

    public String createAccessToken(String domainCd, String userCd, Set<Auth> roles) {
        Claims claims = Jwts.claims().setSubject(userCd);
        claims.put("domainCd", domainCd);
        claims.put("userCd", userCd);
        return Jwts.builder()
                .setClaims(claims)
                .setExpiration(new Date(System.currentTimeMillis() + ACCESS_EXPIRED_TIME))
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

    public Map<String, Object> generateToken(LoginRequest loginRequest) {
        Map<String, Object> resultMap = new LinkedHashMap<String, Object>();
        User userEntity = authRepository.findByUserId(loginRequest.getUserId()).orElseThrow(() ->
                new BadCredentialsException(loginRequest.getUserId() + " : 아이디가 존재하지 않습니다."));
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

}