package com.cotato.homecook.controller;

import com.cotato.homecook.domain.entity.Seller;
import com.cotato.homecook.repository.SellerRepository;
import com.cotato.homecook.repository.ShopRepository;
import com.cotato.homecook.service.ShopService;
import com.cotato.homecook.utils.S3Uploader;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/shops/")
public class ShopController {
    private final S3Uploader s3Uploader;
    private final ShopService shopService;
    @GetMapping("/rank")
    public String getRankTop3(){
        shopService.getRankTop3();
        return "hi";
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
