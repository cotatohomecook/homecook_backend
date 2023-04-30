package com.cotato.homecook.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long customerId;

    // 북마크랑 one to many
    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL)
    List<Bookmark> bookmarks  = new ArrayList<>();

    // 리뷰랑 one to many
    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL)
    List<Review> reviews  = new ArrayList<>();

    // 주문 내역이랑 one to many
    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL)
    List<Order> orders  = new ArrayList<>();

    private String customerName;
    private String email;
    private String password;
    private String phoneNumber;
}
