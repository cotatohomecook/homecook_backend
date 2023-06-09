package com.cotato.homecook.controller;

import com.cotato.homecook.domain.dto.ApiResponse;
import com.cotato.homecook.domain.dto.shop.ShopMapResponse;
import com.cotato.homecook.domain.dto.shop.ShopBestMenuResponse;
import com.cotato.homecook.domain.dto.shop.ShopRankResponse;
import com.cotato.homecook.service.ShopService;
import com.cotato.homecook.utils.S3Uploader;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/shops/")
public class ShopController {
    private final S3Uploader s3Uploader;
    private final ShopService shopService;
//    @GetMapping("/test/{num}")
//    public String test (@PathVariable("num") long num) {
//        List<Object[]> list = menuRepository.findAllByOrderCountByShopId(num);
//        for (Object[] objArr : list) {
//            Menu menu = (Menu) objArr[0];
//            Long count = (Long) objArr[1];
//            System.out.println("Menu name: " + menu.getMenuName() + ", Order count: " + count);
//        }
//        return "hi";
//    }
    @GetMapping("/rank")
    public ApiResponse<List<ShopRankResponse>> getRankTop10(@RequestParam double latitude, @RequestParam double longitude){
        return ApiResponse.createSuccess(shopService.getRankTop10(latitude, longitude));
    }

    @GetMapping("/random")
    public ApiResponse<List<ShopBestMenuResponse>> getRandom10Shops(@RequestParam double latitude, @RequestParam double longitude){
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
    @PostMapping("/image")
    public String updateUserImage(@RequestParam("images") MultipartFile multipartFile) {
        try {
            s3Uploader.uploadFiles(multipartFile, "static");
//        } catch (Exception e) { return new ResponseEntity(HttpStatus.BAD_REQUEST); }
        } catch (Exception e) { return e.getMessage(); }
        return "success";
    }

}
