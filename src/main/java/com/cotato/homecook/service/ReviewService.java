package com.cotato.homecook.service;

import com.cotato.homecook.domain.dto.review.ReviewPatchRequest;
import com.cotato.homecook.domain.dto.review.ReviewPatchResponse;
import com.cotato.homecook.domain.dto.review.ReviewWriteRequest;
import com.cotato.homecook.domain.dto.review.ReviewWriteResponse;
import com.cotato.homecook.domain.entity.OrderHistory;
import com.cotato.homecook.domain.entity.Review;
import com.cotato.homecook.exception.ImageException;
import com.cotato.homecook.repository.ReviewRepository;
import com.cotato.homecook.utils.S3Utils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ReviewService {
    private final S3Utils s3Utils;
    private final ReviewRepository reviewRepository;
    private final ValidateService validateService;

    public ReviewWriteResponse saveReview(ReviewWriteRequest reviewDto) throws ImageException {
        // TODO : 사용자 정보 받아서 적용하는 로직, 사용자가 주문한 기록에 대해서만 리뷰 작성 가능하게끔 처리
        // TODO : 예외처리도 깔끔하게
        // 원래는 주문 번호도 dto에 들어있음
        OrderHistory orderHistory = validateService.validateOrderHistory(reviewDto.getOrderHistoryId());
        validateService.checkDuplicateReview(orderHistory);
        String imageUrl = null;
        if (!(reviewDto.getReviewImage().isEmpty())) {
            imageUrl = s3Utils.uploadFiles(reviewDto.getReviewImage(), "review");
        }

        Review savedReview = reviewRepository.save(reviewDto.toEntity(orderHistory.getCustomer(), imageUrl, orderHistory, orderHistory.getShop()));
        return ReviewWriteResponse.toDto(savedReview);
    }

    @Transactional
    public ReviewPatchResponse patchReview(Long reviewId, ReviewPatchRequest patchRequest){
        Review review = validateService.validateReview(reviewId);
        review.updateContent(patchRequest.getContent());
        return ReviewPatchResponse.toDto(review);
    }
    public ReviewWriteResponse deleteReview(Long reviewId) {
        reviewRepository.deleteById(reviewId);
        return ReviewWriteResponse.builder().reviewId(reviewId).build();
    }

}
