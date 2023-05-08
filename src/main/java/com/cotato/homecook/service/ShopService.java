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
        return shopRepository.findTop10ShopsByOrderCount(latitude, longitude)
                .stream()
                .map(ShopRankResponse::new)
                .collect(Collectors.toList());
    }


    public List<ShopMapResponse> getAllNearShops(double latitude, double longitude) {
        return shopRepository.findAllNearShops(latitude, longitude)
                .stream()
                .map(ShopMapResponse::new)
                .collect(Collectors.toList());
    }

    public List<ShopBestMenuResponse> getRandom10Shops(double latitude, double longitude) {
        return shopRepository.findRadndom10Shops(latitude, longitude)
                .stream()
                .map(this::convertInterfaceToDTO)
                .collect(Collectors.toList());
    }

    public Page<ShopBestMenuResponse> getAllByCategoryByOrderCount(double latitude, double longitude, String category, Pageable pageable) {
        List<ShopDefaultResponseInterface> interfaceList = shopRepository.findAllByCategoryByOrderCount(latitude, longitude, category);
        return convertInterfaceListToPage(interfaceList, pageable);
    }

    public Page<ShopBestMenuResponse> getSearchResultByShopName(double latitude, double longitude, String shopName, String orderBy, Pageable pageable) {
        List<ShopDefaultResponseInterface> interfaceList = new ArrayList<>();
        switch (orderBy) {
            case "orderCount":
                interfaceList = shopRepository.findAllByShopNameOrderByOrderCount(latitude, longitude, shopName);
                break;
            case "distance":
                interfaceList = shopRepository.findAllByShopNameOrderByDistance(latitude, longitude, shopName);
                break;
            case "reviewCount":
                interfaceList = shopRepository.findAlLBYShopNameOrderByReviewCount(latitude, longitude, shopName);
        }
        return convertInterfaceListToPage(interfaceList, pageable);
    }

    private Page<ShopBestMenuResponse> convertInterfaceListToPage(List<ShopDefaultResponseInterface> interfaceList, Pageable pageable) {
        int start = (int) pageable.getOffset();
        int end = Math.min((start + pageable.getPageSize()), interfaceList.size());

        // 검색 결과가 없거나 totalPage를 넘어가면 빈 리스트 반환
        if (start >= interfaceList.size()) {
            return new PageImpl<>(Collections.emptyList(), pageable, interfaceList.size());
        }
        List<ShopBestMenuResponse> dtoList = interfaceList.subList(start, end)
                .stream()
                .map(this::convertInterfaceToDTO)
                .collect(Collectors.toList());
        return new PageImpl<>(dtoList, pageable, interfaceList.size());

    }

    private ShopBestMenuResponse convertInterfaceToDTO(ShopDefaultResponseInterface shopDefaultResponseInterface) {
        // 북마크 확인 코드 추가 필요함
        // get(0)에서 예외 throw 하는 코드 필요
        Menu bestMenu = menuRepository.findBestMenuNameByShopId(shopDefaultResponseInterface.getShop_Id()).get(0);
        return new ShopBestMenuResponse(shopDefaultResponseInterface, bestMenu, false);
    }

}
