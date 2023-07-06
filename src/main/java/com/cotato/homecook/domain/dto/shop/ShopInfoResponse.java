package com.cotato.homecook.domain.dto.shop;

import com.cotato.homecook.domain.dto.menu.ShopOrderMenuResponse;
import com.cotato.homecook.domain.entity.Shop;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class ShopInfoResponse {

    private Long shopId;
    private String shopName;
    private String shopImage;

    private boolean isBookmarked;
    private String receiptImage;
    List<ShopOrderMenuResponse> menus;

    public ShopInfoResponse(Shop shop, List<ShopOrderMenuResponse> menuList, boolean isBookmarked, String imageUrl) {
        shopId = shop.getShopId();
        shopName = shop.getShopName();
        shopImage = shop.getImageUrl();
        this.isBookmarked = isBookmarked;
        receiptImage = imageUrl;
        menus = menuList;
    }
}
