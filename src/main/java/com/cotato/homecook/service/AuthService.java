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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final JwtUtils jwtUtils;
    private final CustomerRepository customerRepository;
    private final SellerRepository sellerRepository;
    private final ValidateService validateService;
    private final BCryptPasswordEncoder passwordEncoder;

    public CustomerJoinResponse createCustomer(CustomerJoinRequest customerJoinRequest) {
        // TODO : 중복 , 유효성 로직 추가 예정
        validateService.checkDuplicateEmail(customerJoinRequest.getEmail());
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
            String accessToken = jwtUtils.createToken(userDto, "access");
            String refreshToken = jwtUtils.createToken(userDto, "refresh");
            validateService.updateUserRefreshToken(userDto, refreshToken);
            return LoginResponse.builder()
                    .accessToken(accessToken)
                    .refreshToken(refreshToken)
                    .build();
        } else {
            throw new AppException(ErrorCode.INCORRECT_PASSWORD);
        }
    }

    public ReissueResponse reissue(ReissueRequest reissueRequest) {
        String refreshToken = reissueRequest.getRefreshToken();
        if (jwtUtils.validateToken(refreshToken)) {
            String email = jwtUtils.getEmailFromToken(refreshToken);
            UserDto userDto = validateService.validateUserByEmail(email);
            if (userDto.getRefreshToken() != null && userDto.getRefreshToken().equals(refreshToken)) {
                return new ReissueResponse(jwtUtils.createToken(userDto, "access"));
            }
            throw new AppException(ErrorCode.WRONG_JWT_TOKEN);
        }
        throw new AppException(ErrorCode.WRONG_JWT_TOKEN);
    }
}