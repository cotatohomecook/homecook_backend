package com.cotato.homecook.domain.dto.shop;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ShopRandomResponse {
    long shopId;
    String shopName;
    String imageUrl;
    String bestMenuName;

    public ShopRandomResponse(ShopRandomResponseInterface shopRandomResponseInterface,String menuName) {
        shopId = shopRandomResponseInterface.getShopId();
        shopName = shopRandomResponseInterface.getShopName();
        imageUrl = shopRandomResponseInterface.getImageUrl();
        bestMenuName = menuName;
    }
}
