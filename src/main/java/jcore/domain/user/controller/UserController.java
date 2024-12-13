package jcore.domain.user.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jcore.common.dto.Response;
import jcore.domain.auth.service.AuthService;
import jcore.domain.user.model.dto.request.LoginRequest;
import jcore.domain.user.model.dto.request.SaveUserRequest;
import jcore.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

import static jcore.common.CommonUtils.okResponsePackaging;

@Slf4j
@Tag(name = "UserController", description = "유저 API")
@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final AuthService authService;
    @PostMapping
    @Operation(summary="유저 저장", description="유저 저장 API")
    public ResponseEntity<Response> saveUser(@RequestBody SaveUserRequest saveUserRequest) throws Exception {
        return okResponsePackaging(userService.saveUser(saveUserRequest));
    }
    @PostMapping(value = "/login")
    @Operation(summary="로그인", description="가입한 회원을 로그인 하는 API")
    public ResponseEntity<Response> login(@RequestBody LoginRequest loginRequest) throws Exception {
        return okResponsePackaging(authService.generateToken(loginRequest));
    }
    @PostMapping(value = "/login/refresh")
    @Operation(summary="로그인 갱신", description="로그인을 갱신 하는 API")
    public ResponseEntity<Response> loginRefresh(HttpServletRequest request) throws Exception {
        return okResponsePackaging(authService.refreshToken(request));
    }
    @GetMapping(value = "/me")
    @Operation(summary="내 정보 보기", description="가입한 회원 정보를 가져오는 API(jwt 인증 요구)")
    public ResponseEntity<Response> getMyInfo(HttpServletRequest request) {
        return okResponsePackaging(userService.getMyInfo(request));
    }
    @GetMapping(value = "/users")
    @Operation(summary="유저 조회", description="유저 ID 유저닉네임 유저네임 유저 핸드폰 번호를 통해 유저정보를 조회한다")
    public ResponseEntity<Response> getUsersWithPageable(HttpServletRequest request, String queryString, Pageable page) {
        return okResponsePackaging(userService.getUsersWithPageable(request, queryString, page));
    }
    @GetMapping(value = "/grid")
    @Operation(summary="유저 그리드", description="유저 그리드")
    public Map<String, Object> userGrid(HttpServletRequest request, @RequestParam HashMap<String, Object> mapParam) {
        return userService.userGrid(request, mapParam);
    }
}