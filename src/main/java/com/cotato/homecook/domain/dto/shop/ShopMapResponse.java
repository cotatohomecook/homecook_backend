package com.cotato.homecook.domain.dto.shop;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ShopMapResponse {
    long shopId;
    String shopName;
    String imageUrl;
    double latitude;
    double longitude;
    double rating;
    long reviewCount;
    long distance;

}
