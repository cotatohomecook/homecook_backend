package com.cotato.homecook.controller;

import com.cotato.homecook.domain.dto.ApiResponse;
import com.cotato.homecook.domain.dto.review.ReviewWriteRequest;
import com.cotato.homecook.exception.ImageException;
import com.cotato.homecook.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/reviews")
public class ReviewController {
    private final ReviewService reviewService;

    @PostMapping("/")
    public ApiResponse<?> writeReview(@ModelAttribute ReviewWriteRequest reviewDto) throws ImageException {
        reviewService.saveReview(reviewDto);
        return ApiResponse.createSuccessWithNoData("리뷰 작성 성공");
    }
}
