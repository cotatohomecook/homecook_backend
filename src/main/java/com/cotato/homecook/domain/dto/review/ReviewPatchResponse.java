package com.cotato.homecook.domain.dto.review;

import com.cotato.homecook.domain.entity.Review;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReviewPatchResponse {
    Long reviewId;
    String content;
    private double rating;

    public static ReviewPatchResponse toDto(Review review) {
        return ReviewPatchResponse.builder().reviewId(review.getReviewId())
                .content(review.getContent())
                .rating(review.getRating()).build();
    }
}
