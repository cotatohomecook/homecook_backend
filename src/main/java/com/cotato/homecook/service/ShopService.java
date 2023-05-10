package com.cotato.homecook.service;

import com.cotato.homecook.domain.dto.shop.*;
import com.cotato.homecook.domain.entity.Menu;
import com.cotato.homecook.repository.MenuRepository;
import com.cotato.homecook.repository.ShopRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ShopService {
    private final ShopRepository shopRepository;
    private final MenuRepository menuRepository;

    public List<ShopRankResponse> getRankTop10(double latitude, double longitude) {
        return shopRepository.findTop10ShopsByOrderCount(latitude, longitude);
    }

    public List<ShopMapResponse> getAllNearShops(double latitude, double longitude) {
        return shopRepository.findAllNearShops(latitude, longitude);
    }

    public List<ShopBestMenuResponse> getRandom10Shops(double latitude, double longitude) {
        return shopRepository.findRadndom10Shops(latitude, longitude)
                .stream()
                .map(this::getBestMenuByShopDto)
                .collect(Collectors.toList());
    }

    public Page<ShopBestMenuResponse> getAllByCategoryByOrderCount(double latitude, double longitude, String category, Pageable pageable) {
        Page<ShopBestMenuResponse> shopPageObject = shopRepository.findAllByCategoryByOrderCount(latitude, longitude, category, pageable);

        List<ShopBestMenuResponse> dtoList = shopPageObject.stream().collect(Collectors.toList())
                .stream().map(this::getBestMenuByShopDto).collect(Collectors.toList());
        return new PageImpl<>(dtoList, pageable, shopPageObject.getTotalElements());
    }

    public Page<ShopBestMenuResponse> getSearchResultByShopName(double latitude, double longitude, String shopName, String orderBy, Pageable pageable) {
        Page<ShopBestMenuResponse> shopPageObject = new PageImpl<>(Collections.emptyList());
        switch (orderBy) {
            case "orderCount":
                shopPageObject = shopRepository.findAllByShopNameOrderByOrderCount(latitude, longitude, shopName, pageable);
                break;
            case "distance":
                shopPageObject = shopRepository.findAllByShopNameOrderByDistance(latitude, longitude, shopName, pageable);
                break;
            case "reviewCount":
                shopPageObject = shopRepository.findAlLBYShopNameOrderByReviewCount(latitude, longitude, shopName, pageable);
        }
        List<ShopBestMenuResponse> dtoList = shopPageObject.stream().collect(Collectors.toList())
                .stream().map(this::getBestMenuByShopDto).collect(Collectors.toList());
        return new PageImpl<>(dtoList, pageable, shopPageObject.getTotalElements());
    }

    private ShopBestMenuResponse getBestMenuByShopDto(ShopBestMenuResponse shopBestMenuResponse) {
        // 북마크 확인 코드 추가 필요함
        // get(0)에서 예외 throw 하는 코드 필요
        Menu bestMenu = menuRepository.findBestMenuNameByShopId(shopBestMenuResponse.getShopId()).get(0);
        shopBestMenuResponse.setBestMenuName(bestMenu.getMenuName());
        shopBestMenuResponse.setBestMenuPrice(bestMenu.getPrice());
        return shopBestMenuResponse;
    }

}
