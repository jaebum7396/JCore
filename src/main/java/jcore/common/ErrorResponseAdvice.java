package jcore.common;

import java.util.LinkedHashMap;
import java.util.Map;

import io.jsonwebtoken.ExpiredJwtException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import jcore.common.dto.Response;

@Slf4j
@RestControllerAdvice
public class ErrorResponseAdvice {
	@ExceptionHandler(Exception.class)
	public ResponseEntity<Response> handleException(Exception e) {
		Response responseResult;
        Map<String, Object> resultMap = new LinkedHashMap<String, Object>();
        responseResult = Response.builder()
                .message("서버쪽 오류가 발생했습니다. 관리자에게 문의하십시오")
                .result(resultMap).build();
        return ResponseEntity.internalServerError().body(responseResult);
	}
	
	@ExceptionHandler(BadCredentialsException.class)
	public ResponseEntity<Response> handleBadCredentialsException(BadCredentialsException e) {
		Response responseResult;
        Map<String, Object> resultMap = new LinkedHashMap<String, Object>();
        responseResult = Response.builder()
                .message(e.getMessage())
                .result(resultMap).build();
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(responseResult);
	}

	@ExceptionHandler(ExpiredJwtException.class)
	public ResponseEntity<Response> handleExpiredJwtException(ExpiredJwtException e) {
		Response responseResult;
		Map<String, Object> resultMap = new LinkedHashMap<String, Object>();
		responseResult = Response.builder()
				.message("로그인 시간이 만료되었습니다.")
				.result(resultMap).build();
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(responseResult);
	}
}
