package com.cotato.homecook.service;

import com.cotato.homecook.domain.dto.review.ReviewWriteRequest;
import com.cotato.homecook.domain.entity.Customer;
import com.cotato.homecook.domain.entity.OrderHistory;
import com.cotato.homecook.repository.CustomerRepository;
import com.cotato.homecook.repository.OrderHistoryRepository;
import com.cotato.homecook.repository.ReviewRepository;
import com.cotato.homecook.repository.ShopRepository;
import com.cotato.homecook.utils.S3Uploader;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ReviewService {
    private final S3Uploader s3Uploader;
    private final ReviewRepository reviewRepository;
    private final ValidateService validateService;

    public String saveReview(ReviewWriteRequest reviewDto) {
        // TODO : 사용자 정보 받아서 적용하는 로직, 사용자가 주문한 기록에 대해서만 리뷰 작성 가능하게끔 처리
        // TODO : 예외처리도 깔끔하게
        String imageUrl = null;
        if (reviewDto.getReviewImage() != null) {
            try {
                imageUrl = s3Uploader.uploadFiles(reviewDto.getReviewImage(), "review");
            } catch (IOException e) {
                return e.getMessage();
            }
        }

        // 원래는 주문 번호도 dto에 들어있음
        // TODO : validate service로 분리
        OrderHistory orderHistory = validateService.findOrderHistoryById(reviewDto.getOrderHistoryId());
        validateService.checkDuplicateReview(orderHistory);
        reviewRepository.save(reviewDto.toEntity(orderHistory.getCustomer(), imageUrl, orderHistory, orderHistory.getShop()));
        return imageUrl;
    }

}
