package com.cotato.homecook.service;

import com.cotato.homecook.domain.dto.shop.ShopMapResponse;
import com.cotato.homecook.domain.dto.shop.ShopRandomResponse;
import com.cotato.homecook.domain.dto.shop.ShopRankResponse;
import com.cotato.homecook.repository.MenuRepository;
import com.cotato.homecook.repository.OrderHistoryRepository;
import com.cotato.homecook.repository.ShopRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ShopService {
    private final ShopRepository shopRepository;
    private final MenuRepository menuRepository;
    private final OrderHistoryRepository orderHistoryRepository;

    public List<ShopRankResponse> getRankTop10(double latitude, double longitude) {
        return shopRepository.findTop10ShopsByOrderCount(latitude, longitude)
                .stream()
                .map(ShopRankResponse::new)
                .collect(Collectors.toList());
    }

    public List<ShopRandomResponse> getRandom10Shops(double latitude, double longitude) {
        return shopRepository.findRadndom10Shops(latitude, longitude)
                .stream()
                .map(s -> {
                    String bestMenuName = menuRepository.findBestMenuNameByShopId(s.getShopId()).get(0);
                    return new ShopRandomResponse(s,bestMenuName);
                })
                .collect(Collectors.toList());
    }

    public List<ShopMapResponse> getAllNearShops(double latitude, double longitude) {
        return shopRepository.findAllNearShops(latitude, longitude)
                .stream()
                .map(ShopMapResponse::new)
                .collect(Collectors.toList());
    }

}
