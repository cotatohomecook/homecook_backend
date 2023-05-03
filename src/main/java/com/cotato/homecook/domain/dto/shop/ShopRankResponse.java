package com.cotato.homecook.domain.dto.shop;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ShopRankResponse {
    long shopId;
    String shopName;
    long orderCount;
    long ranking;
    public ShopRankResponse(ShopRankResponseInterface shopRankResponseInterface) {
        shopId = shopRankResponseInterface.getShopId();
        shopName = shopRankResponseInterface.getShopName();
        orderCount = shopRankResponseInterface.getOrderCount();
        ranking = shopRankResponseInterface.getRanking();
    }
}
