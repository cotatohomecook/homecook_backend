package com.cotato.homecook.repository;

import com.cotato.homecook.domain.dto.shop.ShopBestMenuResponse;
import com.cotato.homecook.domain.dto.shop.ShopDefaultResponseInterface;
import com.cotato.homecook.domain.dto.shop.ShopMapResponse;
import com.cotato.homecook.domain.dto.shop.ShopRankResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CustomShopRepository {
    List<ShopRankResponse> findTop10ShopsByOrderCount(double userLatitude, double userLongitude);
    List<ShopBestMenuResponse> findRadndom10Shops(double userLatitude, double userLongitude);
    List<ShopMapResponse> findAllNearShops(double userLatitude, double userLongitude);
    Page<ShopBestMenuResponse> findAllByCategoryByOrderCount(double latitude, double longitude, String category, Pageable pageable);
    Page<ShopBestMenuResponse> findAllByShopNameOrderByOrderCount(double userLatitude, double userLongitude, String shopName, Pageable pageable);
    Page<ShopBestMenuResponse> findAllByShopNameOrderByDistance(double userLatitude, double userLongitude, String shopName, Pageable pageable);
    Page<ShopBestMenuResponse> findAlLBYShopNameOrderByReviewCount(double userLatitude, double userLongitude, String shopName, Pageable pageable);
}
