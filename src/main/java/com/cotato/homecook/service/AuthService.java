package com.cotato.homecook.service;

import com.cotato.homecook.domain.dto.auth.CustomerJoinRequest;
import com.cotato.homecook.domain.dto.auth.CustomerJoinResponse;
import com.cotato.homecook.domain.entity.Customer;
import com.cotato.homecook.enums.Role;
import com.cotato.homecook.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final CustomerRepository customerRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public CustomerJoinResponse createCustomer(CustomerJoinRequest customerJoinRequest) {
        // TODO : 중복 , 유효성 로직 추가 예정
        Customer savedCustomer = customerRepository.save(Customer.builder()
                .email(customerJoinRequest.getEmail())
                .password(bCryptPasswordEncoder.encode(customerJoinRequest.getPassword()))
                .customerName(customerJoinRequest.getCustomerName())
                .role(Role.ROLE_CUSTOMER)
                .build());

        return new CustomerJoinResponse(savedCustomer.getCustomerId());
    }
}
