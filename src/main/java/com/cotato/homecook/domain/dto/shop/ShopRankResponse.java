package com.cotato.homecook.domain.dto.shop;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ShopRankResponse {
    long shopId;
    String shopName;
    long orderCount;
}
