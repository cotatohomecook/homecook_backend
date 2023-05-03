package com.cotato.homecook.domain.dto.shop;

import com.cotato.homecook.domain.entity.Shop;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ShopMapResponse {
    private long shopId;
    private String shopName;
    private String imageUrl;
    private double latitude;
    private double longitude;
    private double rating;
    public static ShopMapResponse toDto(Shop shop, double rating){
        return ShopMapResponse.builder()
                .shopId(shop.getShopId())
                .shopName(shop.getShopName())
                .imageUrl(shop.getImageUrl())
                .latitude(shop.getLatitude())
                .longitude(shop.getLongitude())
                .rating(rating)
                .build();
    }
}
