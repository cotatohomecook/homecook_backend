package com.cotato.homecook.config.auth;

import com.cotato.homecook.domain.entity.Customer;
import com.cotato.homecook.domain.entity.Seller;
import com.cotato.homecook.exception.AppException;
import com.cotato.homecook.exception.ErrorCode;
import com.cotato.homecook.repository.CustomerRepository;
import com.cotato.homecook.repository.SellerRepository;
import com.cotato.homecook.service.ValidateService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PrincipalDetailsService implements UserDetailsService {
    private final CustomerRepository customerRepository;
    private final SellerRepository sellerRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<Customer> customer = customerRepository.findByEmail(email);
        if (customer.isPresent()) {
            return new PrincipalDetails(customer.get().getEmail(), customer.get().getPassword(), customer.get().getRole().value());
        } else {
            Optional<Seller> seller = sellerRepository.findByEmail(email);
            if (seller.isPresent()) {
                return new PrincipalDetails(seller.get().getEmail(), seller.get().getPassword(), seller.get().getRole().value());
            }
        }
        throw new AppException(ErrorCode.USER_NOT_FOUND);
    }
}
