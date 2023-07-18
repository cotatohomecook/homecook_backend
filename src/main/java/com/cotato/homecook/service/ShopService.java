package com.cotato.homecook.service;

import com.cotato.homecook.domain.dto.menu.ShopDailyBestMenuResponse;
import com.cotato.homecook.domain.dto.menu.ShopOrderMenuResponse;
import com.cotato.homecook.domain.dto.receipt.ReceiptUploadRequest;
import com.cotato.homecook.domain.dto.receipt.ReceiptUploadResponse;
import com.cotato.homecook.domain.dto.shop.*;
import com.cotato.homecook.domain.entity.Customer;
import com.cotato.homecook.domain.entity.Menu;
import com.cotato.homecook.domain.entity.Receipt;
import com.cotato.homecook.domain.entity.Shop;
import com.cotato.homecook.exception.AppException;
import com.cotato.homecook.exception.ErrorCode;
import com.cotato.homecook.exception.ImageException;
import com.cotato.homecook.repository.*;
import com.cotato.homecook.utils.S3Utils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ShopService {
    private final S3Utils s3Utils;
    private final ShopRepository shopRepository;
    private final MenuRepository menuRepository;
    private final BookmarkRepository bookmarkRepository;
    private final CustomerRepository customerRepository;
    private final ReceiptRepository receiptRepository;
    private final ValidateService validateService;

    public ReceiptUploadResponse uploadReceipt(ReceiptUploadRequest receiptUploadRequest, Long shopId) throws ImageException {
        Shop shop = validateService.validateShop(shopId);
        validateService.checkDuplicateReceipt(shop);
        String imageUrl = null;
        if (!(receiptUploadRequest.getReceiptImage().isEmpty())) {
            imageUrl = s3Utils.uploadFiles(receiptUploadRequest.getReceiptImage(), "receipt");
        }
        else {
            throw new AppException(ErrorCode.IMAGE_PROCESSING_FAIL);
        }
        Receipt savedReceipt = receiptRepository.save(new Receipt(imageUrl, shop));
        return ReceiptUploadResponse.toDto(savedReceipt);
    }

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

    public List<ShopDailyBestMenuResponse> getShopDailyBestMenus(String date) {
        // TODO: 자기 자신의 가게의 ID를 불러오게 하기
        Long shopId = 10L;
        LocalDate currentDate = LocalDate.now();  // 현재 날짜
        LocalDate inputDate = LocalDate.parse(date); // 입력 받은 날짜(문자열)를 LocalDateTime 타입으로 변경
        if (currentDate.equals(inputDate)) return getBestMenu2Days(inputDate, shopId);
        else return getBestMenu3Days(inputDate, shopId);
    }

    // 전일과 당일의 베스트 메뉴를 불러오는 메서드
    private List<ShopDailyBestMenuResponse> getBestMenu2Days(LocalDate inputDate, Long shopId) {
        ArrayList<ShopDailyBestMenuResponse> dailyBestMenus = new ArrayList<>();
        dailyBestMenus.add(getShopDailyBestMenuResponse(inputDate.minusDays(1), shopId));
        dailyBestMenus.add(getShopDailyBestMenuResponse(inputDate, shopId));
        return dailyBestMenus;
    }

    // 전일과 당일과 명일의 베스트 메뉴를 불러오는 메서드
    private List<ShopDailyBestMenuResponse> getBestMenu3Days(LocalDate inputDate, Long shopId) {
        ArrayList<ShopDailyBestMenuResponse> dailyBestMenus = new ArrayList<>();
        dailyBestMenus.add(getShopDailyBestMenuResponse(inputDate.minusDays(1), shopId));
        dailyBestMenus.add(getShopDailyBestMenuResponse(inputDate, shopId));
        dailyBestMenus.add(getShopDailyBestMenuResponse(inputDate.plusDays(1), shopId));
        return dailyBestMenus;
    }

    private ShopDailyBestMenuResponse getShopDailyBestMenuResponse(LocalDate inputDate, Long shopId) {
        ShopDailyBestMenuResponse dailyBestMenu = menuRepository.findDailyBestMenuByDateAndShopId(inputDate.minusDays(1), shopId);
        if (dailyBestMenu == null) { // 존재하지 않는다면
            return new ShopDailyBestMenuResponse(inputDate); // 날짜만 반환
        } else {
            dailyBestMenu.setLocalDate(inputDate);
            return dailyBestMenu;
        }
    }
}
