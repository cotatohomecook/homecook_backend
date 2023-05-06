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
    double distance;

    public ShopMapResponse(ShopDefaultResponseInterface shopDefaultResponseInterface) {
        this.shopId = shopDefaultResponseInterface.getShop_Id();
        this.shopName = shopDefaultResponseInterface.getShop_Name();
        this.imageUrl = shopDefaultResponseInterface.getImage_Url();
        this.latitude = shopDefaultResponseInterface.getLatitude();
        this.longitude = shopDefaultResponseInterface.getLongitude();
        this.rating = shopDefaultResponseInterface.getRating();
        this.reviewCount = shopDefaultResponseInterface.getReviewCount();
        this.distance = shopDefaultResponseInterface.getDistance();
    }
}
