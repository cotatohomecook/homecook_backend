package com.cotato.homecook.domain.dto.review;

import com.cotato.homecook.domain.entity.Customer;
import com.cotato.homecook.domain.entity.OrderHistory;
import com.cotato.homecook.domain.entity.Review;
import com.cotato.homecook.domain.entity.Shop;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReviewWriteRequest {
    String content;
    double rating;
    long orderHistoryId;
    MultipartFile reviewImage;

    public Review toEntity(Customer customer, String imageUrl, OrderHistory orderHistory, Shop shop) {
        return Review.builder()
                .is_deleted(false)
                .rating(rating)
                .content(content)
                .customer(customer)
                .imageUrl(imageUrl)
                .orderHistory(orderHistory)
                .shop(shop)
                .build();
    }
}
