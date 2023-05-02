package com.cotato.homecook.service;

import com.cotato.homecook.domain.entity.Shop;
import com.cotato.homecook.repository.ShopRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.awt.print.Pageable;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ShopService {
    private final ShopRepository shopRepository;

    public void getRankTop3() {
        List<Object[]> rankList = shopRepository.findTop3ShopsByOrderCount(PageRequest.of(0, 3), 37.602643, 126.924805);
        for (Object[] shopData : rankList) {
            System.out.println("shopData.toString() = " + shopData.toString());
            System.out.println("shopData[0].toString() = " + shopData[0].toString());
            System.out.println("shopData[1].toString() = " + shopData[1].toString());
            System.out.println("shopData[2].toString() = " + shopData[2].toString());
//            Shop shop = (Shop) shopData[0];
//            Long orderCount = (Long) shopData[1];
//            System.out.println("Shop: " + shop.getShopName() + ", Order count: " + orderCount);
        }
        return;
    }
}
