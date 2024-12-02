package jCore.common;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jCore.common.dto.Response;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Component;
import jCore.configuration.JacksonConfig;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.text.DecimalFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Component
@Slf4j
public class CommonUtils {
    @Value("${jwt.secret.key}")
    private String JWT_SECRET_KEY;

    /**
     * <h3>성공 응답을 포장하는 ResponseEntity를 생성합니다.</h3>
     */
    public ResponseEntity<Response> okResponsePackaging(Map<String, Object> result) {
        Response response = Response.builder()
                .message("요청 성공")
                .result(result).build();
        return ResponseEntity.ok().body(response);
    }

    /**
     * <h3>HttpServletRequest의 인증 헤더에서 JWT 클레임을 추출하고 파싱합니다.</h3>
     */
    public Claims getClaims(HttpServletRequest request){
        try{
            Key secretKey = Keys.hmacShaKeyFor(JWT_SECRET_KEY.getBytes(StandardCharsets.UTF_8));
            Claims claim = Jwts.parserBuilder().setSigningKey(secretKey).build()
                    .parseClaimsJws(request.getHeader("authorization")).getBody();
            return claim;
        } catch (ExpiredJwtException e) {
            throw new ExpiredJwtException(null, null, "로그인 시간이 만료되었습니다.");
        } catch (Exception e) {
            throw new BadCredentialsException("인증 정보에 문제가 있습니다.");
        }
    }

    /**
     * <h3>HttpServletRequest의 파라미터 맵을 변환합니다.</h3>
     */
    public static HashMap<String, Object> convertRequestParameterMap(HttpServletRequest request) {
        HashMap<String, Object> mapParam = new HashMap<String, Object>();
        try {
            // 파라미터 추가
            Enumeration<String> params = request.getParameterNames();
            while (params.hasMoreElements()) {
                String key = params.nextElement();
                System.out.println("key : " +request.getParameter(key));
                mapParam.put(key, request.getParameter(key));
            }

        } catch (Exception e) {
            log.debug("{}", e.toString());
        }
        return mapParam;
    }

    /**
     * <h3>객체를 JSON 형태의 문자열로 반환합니다.</h3>
     */
    public static String getJsonPretty(Object p_obj) {
        String strReturn = "";
        try {
            if (p_obj != null) {
                strReturn = CommonUtils.getString(JacksonConfig.objectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(p_obj));
            }
        } catch (Exception e) {
            log.debug("{}", e.toString());
        }
        return strReturn;
    }

    /**
     * <h3>객체를 문자열로 변환합니다.</h3>
     */
    public static String getString(Object p_Object) {
        String strReturn = "";

        try {
            if (p_Object != null) {
                strReturn = StringUtils.trimToEmpty(p_Object.toString());
            }

        } catch (Exception e) {
            log.debug("{}", e.toString());
        }
        return strReturn;
    }

    /**
     * <h3>요청 바디를 문자열로 읽어옵니다.</h3>
     */
    public static String readRequestBody(CachedBodyHttpServletWrapper requestWrapper) {
        try (BufferedReader reader = requestWrapper.getReader()) {
            return reader.lines().collect(Collectors.joining(System.lineSeparator()));
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }
    }

    /**
     * <h3>타임스탬프를 LocalDateTime으로 변환합니다.</h3>
     */
    public static LocalDateTime convertTimestampToDateTime(long timestamp) {
        Instant instant = Instant.ofEpochMilli(timestamp);
        return LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
    }

    /**
     * <h3>BigDecimal dollar 포맷.</h3>
     */
    public static String getDollarFormat(String amount) {
        // 숫자 포맷 지정
        DecimalFormat df = new DecimalFormat("#,###.##");
        return "$" + df.format(Double.parseDouble(amount));
    }
}
