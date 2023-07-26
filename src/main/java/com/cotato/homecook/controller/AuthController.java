package com.cotato.homecook.controller;

import com.cotato.homecook.domain.dto.ApiResponse;
import com.cotato.homecook.domain.dto.auth.*;
import com.cotato.homecook.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {
    private final AuthService authService;

    @PostMapping("/join/customer")
    public ApiResponse<CustomerJoinResponse> joinCustomer(@RequestBody CustomerJoinRequest customerJoinRequest) {
        return ApiResponse.createSuccess(authService.createCustomer(customerJoinRequest));
    }

    @PostMapping("/login")
    public ApiResponse<LoginResponse> login(@RequestBody LoginRequest loginRequest) {
        return ApiResponse.createSuccess(authService.login(loginRequest));
    }

    @PostMapping("/reissue")
    public ApiResponse<ReissueResponse> reissue(@RequestBody ReissueRequest reissueRequest) {
        return ApiResponse.createSuccess(authService.reissue(reissueRequest));
    }
}
