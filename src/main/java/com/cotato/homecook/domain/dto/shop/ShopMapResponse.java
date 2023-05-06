package com.cotato.homecook.domain.dto.shop;

import com.cotato.homecook.domain.entity.Shop;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
public class ShopMapResponse {
    Long shopId;
    String shopName;
    String imageUrl;
    double latitude;
    double longitude;
    double rating;
    long reviewCount;

    public ShopMapResponse(ShopMapResponseInterface shopMapResponseInterface) {
        this.shopId = shopMapResponseInterface.getShop_Id();
        this.shopName = shopMapResponseInterface.getShop_Name();
        this.imageUrl = shopMapResponseInterface.getImage_Url();
        this.latitude = shopMapResponseInterface.getLatitude();
        this.longitude = shopMapResponseInterface.getLongitude();
        this.rating = shopMapResponseInterface.getRating();
        this.reviewCount = shopMapResponseInterface.getReviewCount();
    }
}
