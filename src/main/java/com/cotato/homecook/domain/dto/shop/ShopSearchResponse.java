package com.cotato.homecook.domain.dto.shop;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ShopSearchResponse {
    long shopId;
    String getShop_Name;
    String getImage_Url;
    double getLatitude;
    double getLongitude;
    double getRating;
    long getReviewCount;
    boolean isBookmarked;
}
