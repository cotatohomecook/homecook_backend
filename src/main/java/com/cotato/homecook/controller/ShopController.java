package com.cotato.homecook.controller;

import com.cotato.homecook.utils.S3Uploader;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
public class ShopController {
    private final S3Uploader s3Uploader;
    @GetMapping("/")
    public String test() {
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
