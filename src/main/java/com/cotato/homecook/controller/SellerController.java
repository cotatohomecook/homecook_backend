package com.cotato.homecook.controller;

import com.cotato.homecook.domain.dto.ApiResponse;
import com.cotato.homecook.domain.dto.seller.DocumentValidationRequest;
import com.cotato.homecook.service.ValidateService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/sellers")
public class SellerController {
    private final ValidateService validateService;
    @PostMapping("/document")
    public ApiResponse<?> validateDocument(@RequestBody DocumentValidationRequest documentValidationRequest){
        return ApiResponse.createSuccess(validateService.validateDocument(documentValidationRequest));
    }
}
