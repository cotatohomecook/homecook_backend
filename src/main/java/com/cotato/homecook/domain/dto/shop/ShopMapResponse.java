package com.cotato.homecook.domain.dto.shop;

import com.cotato.homecook.domain.entity.Shop;
import lombok.AllArgsConstructor;
import lombok.Builder;
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
