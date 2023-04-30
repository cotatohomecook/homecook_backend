package com.cotato.homecook.controller;

import com.cotato.homecook.domain.entity.Seller;
import com.cotato.homecook.repository.SellerRepository;
import com.cotato.homecook.repository.ShopRepository;
import com.cotato.homecook.utils.S3Uploader;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class ShopController {
    private final S3Uploader s3Uploader;
    private final SellerRepository sellerRepository;
    private final ShopRepository shopRepository;
    @GetMapping("/")
    public String test() {
        Optional<Seller> seller = sellerRepository.findById(1L);
        String s = seller.get().getShop().getShopName().toString();
        System.out.println("s = " + s);
        shopRepository.deleteById(2L);
        return "test";
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
