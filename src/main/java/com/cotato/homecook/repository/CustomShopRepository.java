package com.cotato.homecook.repository;

import com.cotato.homecook.domain.dto.shop.ShopDefaultResponseInterface;
import com.cotato.homecook.domain.dto.shop.ShopRankResponse;

import java.util.List;

public interface CustomShopRepository {
    List<ShopRankResponse> findTop10ShopsByOrderCount(double userLatitude, double userLongitude);
}
