package com.cotato.homecook.controller;

import com.cotato.homecook.domain.dto.ApiResponse;
import com.cotato.homecook.domain.dto.review.ReviewWriteRequest;
import com.cotato.homecook.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/reviews")
public class ReviewController {
    private final ReviewService reviewService;

    @PostMapping("/")
    public ApiResponse<String> writeReview(@ModelAttribute ReviewWriteRequest reviewDto){
        return ApiResponse.createSuccess(reviewService.saveReview(reviewDto));
    }
}
