package com.cotato.homecook.service;

import com.cotato.homecook.domain.dto.shop.*;
import com.cotato.homecook.repository.MenuRepository;
import com.cotato.homecook.repository.ShopRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
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

    public List<ShopBestMenuResponse> getRandom10Shops(double latitude, double longitude) {
        return shopRepository.findRadndom10Shops(latitude, longitude)
                .stream()
                .map(s -> {
                    Menu bestMenu = menuRepository.findBestMenuNameByShopId(s.getShop_Id()).get(0);
                    return new ShopBestMenuResponse(s, bestMenu);
                })
                .collect(Collectors.toList());
    }

    public List<ShopMapResponse> getAllNearShops(double latitude, double longitude) {
        return shopRepository.findAllNearShops(latitude, longitude)
                .stream()
                .map(ShopMapResponse::new)
                .collect(Collectors.toList());
    }

    public Page<ShopBestMenuResponse> getAllByCategoryByOrderCount(double latitude, double longitude, String category, Pageable pageable) {
        List<ShopDefaultResponseInterface> interfaceList = shopRepository.findAllByCategoryByOrderCount(latitude, longitude, category);
        int start = (int) pageable.getOffset();
        int end = Math.min((start + pageable.getPageSize()), interfaceList.size());
        List<ShopBestMenuResponse> dtoList = interfaceList.subList(start, end)
                .stream()
                .map(s -> {
                    Menu bestMenu = menuRepository.findBestMenuNameByShopId(s.getShop_Id()).get(0);
                    return new ShopBestMenuResponse(s, bestMenu);
                })
                .collect(Collectors.toList());
        return new PageImpl<>(dtoList, pageable, interfaceList.size());
    }
}
