package com.cotato.homecook.domain.dto.auth;

import com.cotato.homecook.domain.entity.Customer;
import com.cotato.homecook.domain.entity.Seller;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserDto {
    String email;
    String password;
    String username;
    String role;
    String refreshToken;

    public UserDto(Customer customer) {
        this.email = customer.getEmail();
        this.password = customer.getPassword();
        this.username = customer.getCustomerName();
        this.role = customer.getRole().value();
        this.refreshToken = customer.getRefreshToken();
    }

    public UserDto(Seller seller) {
        this.email = seller.getEmail();
        this.password = seller.getPassword();
        this.username = seller.getSellerName();
        this.role = seller.getRole().value();
        this.refreshToken = seller.getRefreshToken();
    }
}
