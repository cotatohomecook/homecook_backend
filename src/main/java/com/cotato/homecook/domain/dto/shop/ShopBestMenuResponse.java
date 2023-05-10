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
    double rating;
    long reviewCount;
    long distance;

    public ShopBestMenuResponse(ShopDefaultResponseInterface shopDefaultResponseInterface, Menu menu) {
        shopId = shopDefaultResponseInterface.getShop_Id();
        shopName = shopDefaultResponseInterface.getShop_Name();
        imageUrl = shopDefaultResponseInterface.getImage_Url();
        reviewCount = shopDefaultResponseInterface.getReviewCount();
        rating = shopDefaultResponseInterface.getRating();
        distance = shopDefaultResponseInterface.getDistance();
        bestMenuName = menu.getMenuName();
        bestMenuPrice = menu.getPrice();
    }
}
