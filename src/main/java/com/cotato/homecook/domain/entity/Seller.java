package com.cotato.homecook.domain.entity;

import com.cotato.homecook.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Seller {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long sellerId;

    @OneToOne(mappedBy = "seller", cascade = CascadeType.ALL)
    private Shop shop;
    private String sellerName;
    private String email;
    private String password;
    private String phoneNumber;
    private String postNumber;
    private String address;
    private String bankName;
    private String accountNumber;
    private Date birthDate;
    private String refreshToken;

    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    private Role role;

    public void updateRefreshToken(String refreshToken){
        this.refreshToken = refreshToken;
    }
}
