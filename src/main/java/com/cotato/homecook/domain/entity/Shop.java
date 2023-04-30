package com.cotato.homecook.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Shop {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long shopId;

    // 판매자 ID 매핑, shop이 외래키 저장
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "seller_id", nullable = false)
    private Seller seller;

    // 메뉴랑 one to many
    @OneToMany(mappedBy = "shop", cascade = CascadeType.ALL)
    List<Menu> menus  = new ArrayList<>();

    // 주문이랑 one to many
    @OneToMany(mappedBy = "shop", cascade = CascadeType.ALL)
    List<OrderHistory> orderHistories = new ArrayList<>();

    private String shopName;
    private String imageUrl;
    private double latitude;
    private double longitude;

}
