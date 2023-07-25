package com.cotato.homecook.service;

import com.cotato.homecook.config.auth.PrincipalDetails;
import com.cotato.homecook.config.security.JwtUtils;
import com.cotato.homecook.domain.dto.auth.*;
import com.cotato.homecook.domain.entity.Customer;
import com.cotato.homecook.domain.entity.Seller;
import com.cotato.homecook.enums.Role;
import com.cotato.homecook.exception.AppException;
import com.cotato.homecook.exception.ErrorCode;
import com.cotato.homecook.repository.CustomerRepository;
import com.cotato.homecook.repository.SellerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final CustomerRepository customerRepository;
    private final SellerRepository sellerRepository;
    private final ValidateService validateService;
    private final BCryptPasswordEncoder passwordEncoder;
    private final JwtUtils jwtUtils;

    public CustomerJoinResponse createCustomer(CustomerJoinRequest customerJoinRequest) {
        // TODO : 중복 , 유효성 로직 추가 예정
        Customer savedCustomer = customerRepository.save(Customer.builder()
                .email(customerJoinRequest.getEmail())
                .password(passwordEncoder.encode(customerJoinRequest.getPassword()))
                .customerName(customerJoinRequest.getCustomerName())
                .role(Role.ROLE_CUSTOMER)
                .build());

        return new CustomerJoinResponse(savedCustomer.getCustomerId());
    }

    public LoginResponse login(LoginRequest loginRequest) {
        UserDto userDto = validateService.validateUserByEmail(loginRequest.getEmail());
        if (passwordEncoder.matches(loginRequest.getPassword(), userDto.getPassword())) {
            return LoginResponse.builder()
                    .accessToken(jwtUtils.createToken(userDto.getEmail(), userDto.getRole(), userDto.getUsername(),"access"))
                    .refreshToken(jwtUtils.createToken(userDto.getEmail(), userDto.getRole(), userDto.getUsername(),"refresh"))
                    .build();
        } else {
            throw new AppException(ErrorCode.INCORRECT_PASSWORD);
        }
    }
}