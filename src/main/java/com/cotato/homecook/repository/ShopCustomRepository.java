package com.cotato.homecook.repository;

import com.cotato.homecook.domain.dto.shop.ShopBestMenuResponse;
import com.cotato.homecook.domain.dto.shop.ShopMapResponse;
import com.cotato.homecook.domain.dto.shop.ShopRankResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ShopCustomRepository {
    List<ShopRankResponse> findTop10ShopsByOrderCount(double userLatitude, double userLongitude);
    List<ShopBestMenuResponse> findRadndom10Shops(double userLatitude, double userLongitude);
    List<ShopMapResponse> findAllNearShops(double userLatitude, double userLongitude);
    Page<ShopBestMenuResponse> findAllByCategoryByOrderCount(double userLatitude, double userLongitude, String category, Pageable pageable);
    Page<ShopBestMenuResponse> findAllByShopName(double userLatitude, double userLongitude, String shopName, String orderBy, Pageable pageable);
    Page<ShopBestMenuResponse> findAllBYMenuName(double userLatitude, double userLongitude, String menuName, String orderBy, Pageable pageable);

}
