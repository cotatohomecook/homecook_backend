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

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ReviewService {
    private final S3Uploader s3Uploader;
    private final ReviewRepository reviewRepository;
    private final CustomerRepository customerRepository;
    private final OrderHistoryRepository orderHistoryRepository;
    private final ShopRepository shopRepository;

    public String saveReview(ReviewWriteRequest reviewDto) {
        // TODO : 사용자 정보 받아서 적용, 사용자가 주문한 기록에 대해서만 리뷰 작성 가능하게끔 처리
        // TODO : 예외처리도 깔끔하게
        String imageUrl;
        try {
            imageUrl = s3Uploader.uploadFiles(reviewDto.getReviewImage(), "review");
        } catch (Exception e) {
            return e.getMessage();
        }
//        Optional<Customer> customer = customerRepository.findById(32L);
        // 원래는 주문 번호도 dto에 들어있음
//        Optional<OrderHistory> orderHistory = orderHistoryRepository.findById(reviewDto.getOrderHistoryId());
        Optional<OrderHistory> orderHistory = orderHistoryRepository.findById(3L);
        reviewRepository.save(reviewDto.toEntity(orderHistory.get().getCustomer(), imageUrl, orderHistory.get(), orderHistory.get().getShop()));
        return imageUrl;
    }
}
