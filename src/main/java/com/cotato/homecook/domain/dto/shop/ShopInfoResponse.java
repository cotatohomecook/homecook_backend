package com.cotato.homecook.domain.dto.shop;

import com.cotato.homecook.domain.entity.Shop;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class ShopInfoResponse {

    private Long shopId;
    private String shopName;
    private String imageUrl;

    List<ShopOrderMenuResponse> menus;

    public ShopInfoResponse(Shop shop, List<ShopOrderMenuResponse> menuList) {
        shopId = shop.getShopId();
        shopName = shop.getShopName();
        imageUrl = shop.getImageUrl();
        menus = menuList;
    }
}
