package com.cotato.homecook.service;

import com.cotato.homecook.domain.dto.menu.ShopDailyBestMenuResponse;
import com.cotato.homecook.domain.dto.menu.ShopOrderMenuResponse;
import com.cotato.homecook.domain.dto.shop.*;
import com.cotato.homecook.domain.entity.Customer;
import com.cotato.homecook.domain.entity.Menu;
import com.cotato.homecook.domain.entity.Receipt;
import com.cotato.homecook.domain.entity.Shop;
import com.cotato.homecook.exception.AppException;
import com.cotato.homecook.exception.ErrorCode;
import com.cotato.homecook.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ShopService {
    private final ShopRepository shopRepository;
    private final MenuRepository menuRepository;
    private final BookmarkRepository bookmarkRepository;
    private final CustomerRepository customerRepository;
    private final ReceiptRepository receiptRepository;

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

    public ShopInfoResponse getShopInfo(Long shopId) {
        // TODO: Custom Exception으로 설정
        Customer customer = customerRepository.findById(329329L)
                .orElseThrow(() -> new AppException(ErrorCode.CUSTOMER_NOT_FOUND));
        Shop shop = shopRepository.findById(shopId).orElseThrow(RuntimeException::new);
        List<ShopOrderMenuResponse> menuList = new ArrayList<>();
        shop.getMenus().forEach(menu -> menuList.add(new ShopOrderMenuResponse(menu)));
        boolean isBookmarked = bookmarkRepository.existsByCustomerAndShop(customer, shop);
        Receipt receipt = receiptRepository.findFirstByShopOrderByUploadedAtDesc(shop)
                .orElseThrow(() -> new AppException(ErrorCode.RECEIPT_NOT_FOUND));
        return new ShopInfoResponse(shop, menuList, isBookmarked, receipt.getImageUrl());
    }
    public Page<ShopBestMenuResponse> getSearchResultByShopName(double latitude, double longitude, String shopName, String orderBy, Pageable pageable) {
        Page<ShopBestMenuResponse> shopPageObject = shopRepository.findAllByShopName(latitude, longitude, shopName, orderBy, pageable);
        List<ShopBestMenuResponse> dtoList = shopPageObject.stream().collect(Collectors.toList())
                .stream().map(this::getBestMenuByShopDto).collect(Collectors.toList());

        return new PageImpl<>(dtoList, pageable, shopPageObject.getTotalElements());
    }

    public Page<ShopBestMenuResponse> getSearchResultByMenuName(double latitude, double longitude, String menuName, String orderBy, Pageable pageable) {
        Page<ShopBestMenuResponse> shopPageObject = shopRepository.findAllBYMenuName(latitude, longitude, menuName, orderBy, pageable);
        List<ShopBestMenuResponse> dtoList = shopPageObject.stream().collect(Collectors.toList())
                .stream().map(this::getBestMenuByShopDto).collect(Collectors.toList());

        return new PageImpl<>(dtoList, pageable, shopPageObject.getTotalElements());
    }

    private ShopBestMenuResponse getBestMenuByShopDto(ShopBestMenuResponse shopBestMenuResponse) {
        // 북마크 확인 코드 추가 필요함
        // get(0)에서 예외 throw 하는 코드 필요
        Menu bestMenu = menuRepository.findBestMenuNameByShopId(shopBestMenuResponse.getShopId());
        shopBestMenuResponse.setBestMenuName(bestMenu.getMenuName());
        shopBestMenuResponse.setBestMenuPrice(bestMenu.getPrice());
        return shopBestMenuResponse;
    }

    public ShopDailyBestMenuResponse getShopDailyBestMenu(String date) {
        return null;
    }
}
