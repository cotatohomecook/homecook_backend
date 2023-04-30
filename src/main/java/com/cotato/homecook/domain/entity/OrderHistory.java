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
public class OrderHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderHistoryId;

    // 가게랑 many to one
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "shop_id", nullable = false)
    private Shop shop;

    // 구매자랑 many to one
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

    // 주문 수량이랑 one to many
    @OneToMany(mappedBy = "orderHistory", cascade = CascadeType.ALL)
    List<OrderQuantity> orderQuantities = new ArrayList<>();

    // 리뷰랑 one to one
    @OneToOne(mappedBy = "orderHistory")
    private Review review;

    private Date orderedAt;
    private Boolean isCompleted;
    private Boolean isDeleted;
}
