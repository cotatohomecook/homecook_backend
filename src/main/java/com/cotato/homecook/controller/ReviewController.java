package com.cotato.homecook.controller;

import com.cotato.homecook.domain.dto.ApiResponse;
import com.cotato.homecook.domain.dto.review.ReviewPatchRequest;
import com.cotato.homecook.domain.dto.review.ReviewPatchResponse;
import com.cotato.homecook.domain.dto.review.ReviewWriteRequest;
import com.cotato.homecook.domain.dto.review.ReviewWriteResponse;
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
    public ApiResponse<ReviewWriteResponse> writeReview(@ModelAttribute ReviewWriteRequest reviewDto) throws ImageException {
        return ApiResponse.createSuccess(reviewService.saveReview(reviewDto));
    }

    @PatchMapping("/{reviewId}")
    public ApiResponse<ReviewPatchResponse> patchReview(@PathVariable Long reviewId, @RequestBody ReviewPatchRequest patchRequest){
        return ApiResponse.createSuccess(reviewService.patchReview(reviewId, patchRequest));
    }

    @DeleteMapping("/{reviewId}")
    public ApiResponse<ReviewWriteResponse> deleteReview(@PathVariable Long reviewId){
        return ApiResponse.createSuccess(reviewService.deleteReview(reviewId));
    }
}
