package com.cotato.homecook.domain.dto.shop;

import com.cotato.homecook.domain.entity.Menu;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ShopBestMenuResponse {
    long shopId;
    String shopName;
    String imageUrl;
    String bestMenuName;
    double bestMenuPrice;

    public ShopBestMenuResponse(ShopBestMenuResponseInterface shopBestMenuResponseInterface, Menu menu) {
        shopId = shopBestMenuResponseInterface.getShopId();
        shopName = shopBestMenuResponseInterface.getShopName();
        imageUrl = shopBestMenuResponseInterface.getImageUrl();
        bestMenuName = menu.getMenuName();
        bestMenuPrice = menu.getPrice();
    }
}
