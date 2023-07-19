package com.cotato.homecook.controller;

import com.cotato.homecook.domain.dto.ApiResponse;
import com.cotato.homecook.domain.dto.auth.CustomerJoinRequest;
import com.cotato.homecook.domain.dto.auth.CustomerJoinResponse;
import com.cotato.homecook.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {
    private final AuthService authService;
    @PostMapping("/join/customer")
    public ApiResponse<CustomerJoinResponse> joinCustomer(@RequestBody CustomerJoinRequest customerJoinRequest){
        return ApiResponse.createSuccess(authService.createCustomer(customerJoinRequest));
    }
}
