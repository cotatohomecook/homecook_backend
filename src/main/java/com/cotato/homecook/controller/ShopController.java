package com.cotato.homecook.controller;

import com.cotato.homecook.domain.dto.ApiResponse;
import com.cotato.homecook.domain.dto.menu.ShopDailyBestMenuResponse;
import com.cotato.homecook.domain.dto.shop.ShopInfoResponse;
import com.cotato.homecook.domain.dto.shop.ShopMapResponse;
import com.cotato.homecook.domain.dto.shop.ShopBestMenuResponse;
import com.cotato.homecook.domain.dto.shop.ShopRankResponse;
import com.cotato.homecook.service.ShopService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/shops")
public class ShopController {
    private final ShopService shopService;

    @GetMapping("/rank")
    public ApiResponse<List<ShopRankResponse>> getRankTop10(@RequestParam double latitude, @RequestParam double longitude) {
        return ApiResponse.createSuccess(shopService.getRankTop10(latitude, longitude));
    }

    @GetMapping("/random")
    public ApiResponse<List<ShopBestMenuResponse>> getRandom10Shops(@RequestParam double latitude, @RequestParam double longitude) {
        return ApiResponse.createSuccess(shopService.getRandom10Shops(latitude, longitude));
    }

    @GetMapping("/map")
    public ApiResponse<List<ShopMapResponse>> getAllShops(@RequestParam double latitude, @RequestParam double longitude) {
        return ApiResponse.createSuccess(shopService.getAllNearShops(latitude, longitude));
    }

    @GetMapping("/list")
    public ApiResponse<Page<ShopBestMenuResponse>> getAllByCategoryByOrderCount(@RequestParam double latitude, @RequestParam double longitude,
                                                                                String category, Pageable pageable) {
        return ApiResponse.createSuccess(shopService.getAllByCategoryByOrderCount(latitude, longitude, category, pageable));
    }

    @GetMapping("/search")
    public ApiResponse<Page<ShopBestMenuResponse>> getSearchResultByShopName(@RequestParam double latitude, @RequestParam double longitude,
                                                                             @RequestParam(required = false) String shopName, @RequestParam(required = false) String menuName, @RequestParam String orderBy, Pageable pageable) {
        if (shopName != null) {
            return ApiResponse.createSuccess(shopService.getSearchResultByShopName(latitude, longitude, shopName, orderBy, pageable));
        } else {
            return ApiResponse.createSuccess(shopService.getSearchResultByMenuName(latitude, longitude, menuName, orderBy, pageable));
        }
    }

    @GetMapping("/info/{shopId}")
    public ApiResponse<ShopInfoResponse> getShopInfo(@PathVariable Long shopId){
        return ApiResponse.createSuccess(shopService.getShopInfo(shopId));
    }

    @GetMapping("/dailyBestMenu/{date}")
    public ApiResponse<List<ShopDailyBestMenuResponse>> getShopDailyBestMenus(@PathVariable String date){
        return ApiResponse.createSuccess(shopService.getShopDailyBestMenus(date));
    }
}
