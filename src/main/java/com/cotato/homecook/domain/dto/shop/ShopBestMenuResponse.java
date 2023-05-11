package com.cotato.homecook.domain.dto.shop;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
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


    public ShopBestMenuResponse(long shopId, String shopName, String imageUrl, double rating, long reviewCount, long distance) {
        this.shopId = shopId;
        this.shopName = shopName;
        this.imageUrl = imageUrl;
        this.rating = rating;
        this.reviewCount = reviewCount;
        this.distance = distance;
    }

    @Override
    public String toString() {
        return "ShopBestMenuResponse{" +
                "shopId=" + shopId +
                ", shopName='" + shopName + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                ", bestMenuName='" + bestMenuName + '\'' +
                ", bestMenuPrice=" + bestMenuPrice +
                ", rating=" + rating +
                ", reviewCount=" + reviewCount +
                ", distance=" + distance +
                ", isBookmarked=" + isBookmarked +
                '}';
    }
}
