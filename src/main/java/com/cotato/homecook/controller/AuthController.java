package com.cotato.homecook.controller;

import com.cotato.homecook.domain.dto.ApiResponse;
import com.cotato.homecook.domain.dto.auth.*;
import com.cotato.homecook.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {
    private final StringRedisTemplate stringRedisTemplate;
    private final AuthService authService;

    // TODO
    //  : 만약에 회원가입을 공통으로 진행해서 판매자 구매자 테이블에 전부 정보를 넣어놓는다면?
    //  : 로그인 URL을 역할별로 다르게 해서 같은 유저라도 역할 별 다른 토큰을 쓰게끔 해야함.
    @PostMapping("/join")
    public ApiResponse<JoinResponse> joinCustomer(@RequestBody JoinRequest joinRequest) {
        return ApiResponse.createSuccess(authService.createUser(joinRequest));
    }

    @PostMapping("/login")
    public ApiResponse<LoginResponse> login(@RequestBody LoginRequest loginRequest) {
        return ApiResponse.createSuccess(authService.login(loginRequest));
    }

//    @PostMapping("/reissue")
//    public ApiResponse<ReissueResponse> reissue(@RequestBody ReissueRequest reissueRequest) {
//        return ApiResponse.createSuccess(authService.reissue(reissueRequest));
//    }

    @GetMapping("/test")
    public ApiResponse<String> test() {
        ValueOperations<String, String> opsForValue = stringRedisTemplate.opsForValue();
        opsForValue.set("test","test");
        return ApiResponse.createSuccess("dsaf");
    }
}
