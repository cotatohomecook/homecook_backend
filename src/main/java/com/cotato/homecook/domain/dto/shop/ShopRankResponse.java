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
    public ShopRankResponse(ShopDefaultResponseInterface shopDefaultResponseInterface) {
        shopId = shopDefaultResponseInterface.getShop_Id();
        shopName = shopDefaultResponseInterface.getShop_Name();
        orderCount = shopDefaultResponseInterface.getOrderCount();
        ranking = shopDefaultResponseInterface.getRanking();
    }
}
