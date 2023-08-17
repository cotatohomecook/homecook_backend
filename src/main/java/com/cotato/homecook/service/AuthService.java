package com.cotato.homecook.service;

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
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final JwtUtils jwtUtils;
    private final CustomerRepository customerRepository;
    private final SellerRepository sellerRepository;
    private final ValidateService validateService;
    private final BCryptPasswordEncoder passwordEncoder;

    public JoinResponse createUser(JoinRequest joinRequest) {
        // TODO : 중복 , 유효성 로직 추가 예정
        validateService.checkDuplicateEmail(joinRequest.getEmail());
        Customer savedCustomer = customerRepository.save(Customer.builder()
                .email(joinRequest.getEmail())
                .password(passwordEncoder.encode(joinRequest.getPassword()))
                .customerName(joinRequest.getUsername())
                .role(Role.ROLE_CUSTOMER)
                .build());

        Seller savedSeller = sellerRepository.save(Seller.builder()
                .email(joinRequest.getEmail())
                .password(passwordEncoder.encode(joinRequest.getPassword()))
                .sellerName(joinRequest.getUsername())
                .role(Role.ROLE_SELLER)
                .build());

        return new JoinResponse(savedCustomer.getCustomerId(), savedSeller.getSellerId());
    }

    public LoginResponse login(LoginRequest loginRequest) {
        UserDto userDto = validateService.validateUserByEmail(loginRequest.getEmail());
        if (passwordEncoder.matches(loginRequest.getPassword(), userDto.getPassword())) {
            String customerAccessToken = jwtUtils.createAccessToken(userDto, Role.ROLE_CUSTOMER.value());
            String sellerAccessToken = jwtUtils.createAccessToken(userDto, Role.ROLE_SELLER.value());
            String refreshToken = jwtUtils.createRefreshToken(userDto);

            jwtUtils.updateUserRefreshToken(userDto, refreshToken);
            return LoginResponse.builder()
                    .customerAccessToken(customerAccessToken)
                    .sellerAccessToken(sellerAccessToken)
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
            String userRefreshToken = jwtUtils.getUserRefreshToken(email);
            if (userRefreshToken != null && userRefreshToken.equals(refreshToken)) {
                return new ReissueResponse(jwtUtils.createAccessToken(userDto, reissueRequest.getRole()));
            } else {
                throw new AppException(ErrorCode.WRONG_JWT_TOKEN);
            }
        } else {
            throw new AppException(ErrorCode.WRONG_JWT_TOKEN);
        }
    }

    public void logout(LogoutRequest logoutRequest) {
        String refreshToken = logoutRequest.getRefreshToken();
        if (jwtUtils.validateToken(refreshToken)) {
            String email = jwtUtils.getEmailFromToken(refreshToken);
            jwtUtils.deleteRefreshTokenByEmail(email);
            jwtUtils.setBlackList(logoutRequest.getCustomerAccessToken());
            jwtUtils.setBlackList(logoutRequest.getSellerAccessToken());
        } else {
            throw new AppException(ErrorCode.WRONG_JWT_TOKEN);
        }
    }
}
