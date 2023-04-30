package com.cotato.homecook.domain.entity;

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

    @OneToOne(mappedBy = "seller")
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
}
