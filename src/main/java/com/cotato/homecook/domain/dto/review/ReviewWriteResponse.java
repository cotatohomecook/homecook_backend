package com.cotato.homecook.domain.dto.review;

import com.cotato.homecook.domain.entity.Review;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ReviewWriteResponse {
    Long reviewId;
    public static ReviewWriteResponse toDto(Review review) {
        return ReviewWriteResponse.builder()
                .reviewId(review.getReviewId())
                .build();
    }
}
