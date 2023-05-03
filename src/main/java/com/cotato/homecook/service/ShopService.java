package com.cotato.homecook.service;

import com.cotato.homecook.domain.dto.shop.ShopMapResponse;
import com.cotato.homecook.domain.dto.shop.ShopRandomResponse;
import com.cotato.homecook.domain.dto.shop.ShopRankResponse;
import com.cotato.homecook.domain.entity.OrderHistory;
import com.cotato.homecook.domain.entity.Shop;
import com.cotato.homecook.repository.MenuRepository;
import com.cotato.homecook.repository.OrderHistoryRepository;
import com.cotato.homecook.repository.ReviewRepository;
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
                    ShopRandomResponse shopRandomResponse = new ShopRandomResponse(s);
                    String bestMenuName = menuRepository.findMostPopularMenuNameByShopId(s.getShopId(), PageRequest.of(0, 1)).get(0);
                    shopRandomResponse.setBestMenuName(bestMenuName);
                    return shopRandomResponse;
                })
                .collect(Collectors.toList());
    }

    public List<ShopMapResponse> getAllNearShops(double latitude, double longitude) {
        return shopRepository.findAllNearShops(latitude, longitude)
                .stream()
                .map(s -> ShopMapResponse.toDto(s, getRatingOfShop(s)))
                .collect(Collectors.toList());
    }

    private double getRatingOfShop(Shop shop) {
        return Double.parseDouble(String.format("%.1f", orderHistoryRepository.findAllByShopAndReviewIsNotNull(shop)
                .stream()
                .mapToDouble(o -> o.getReview().getRating())
                .average()
                .orElse(0)));
    }
}
