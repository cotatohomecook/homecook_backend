package com.cotato.homecook.domain.dto.shop;

import com.cotato.homecook.domain.entity.Menu;
import com.fasterxml.jackson.annotation.JsonProperty;
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
    @JsonProperty("isBookmarked")
    boolean isBookmarked;

    public ShopBestMenuResponse(ShopDefaultResponseInterface shopDefaultResponseInterface, Menu menu, boolean isBookmarked) {
        this.shopId = shopDefaultResponseInterface.getShop_Id();
        this.shopName = shopDefaultResponseInterface.getShop_Name();
        this.imageUrl = shopDefaultResponseInterface.getImage_Url();
        this.reviewCount = shopDefaultResponseInterface.getReviewCount();
        this.rating = shopDefaultResponseInterface.getRating();
        this.distance = shopDefaultResponseInterface.getDistance();
        this.bestMenuName = menu.getMenuName();
        this.bestMenuPrice = menu.getPrice();
        this.isBookmarked = isBookmarked;
    }
}
