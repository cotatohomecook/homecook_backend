package com.cotato.homecook.service;

import com.cotato.homecook.config.auth.PrincipalDetails;
import com.cotato.homecook.config.security.JwtUtils;
import com.cotato.homecook.domain.dto.auth.CustomerJoinRequest;
import com.cotato.homecook.domain.dto.auth.CustomerJoinResponse;
import com.cotato.homecook.domain.dto.auth.LoginRequest;
import com.cotato.homecook.domain.dto.auth.LoginResponse;
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
        Optional<Customer> customer = customerRepository.findByEmail(loginRequest.getEmail());
        Optional<Seller> seller = sellerRepository.findByEmail(loginRequest.getEmail());
        if (customer.isPresent()) {
            if (passwordEncoder.matches(loginRequest.getPassword(), customer.get().getPassword()))
                return new LoginResponse(jwtUtils.createToken(loginRequest.getEmail(), customer.get().getRole().value(), customer.get().getCustomerName()));
            else
                throw new AppException(ErrorCode.INCORRECT_PASSWORD);
        } else if (seller.isPresent()) {
            if (passwordEncoder.matches(loginRequest.getPassword(), seller.get().getPassword()))
                return new LoginResponse(jwtUtils.createToken(loginRequest.getEmail(), seller.get().getRole().value(), seller.get().getSellerName()));
            else
                throw new AppException(ErrorCode.INCORRECT_PASSWORD);
        } else {
            throw new AppException(ErrorCode.USER_NOT_FOUND);
        }
    }
}