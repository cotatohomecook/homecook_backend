package com.cotato.homecook.domain.dto.review;

import com.cotato.homecook.domain.entity.Review;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReviewResponse {
    private Long reviewId;
    private String content;
    private double rating;
    private LocalDateTime uploaded_at;
    private String imageUrl;

    public static ReviewResponse toDto(Review review) {
        return ReviewResponse.builder()
                .reviewId(review.getReviewId())
                .content(review.getContent())
                .rating(review.getRating())
                .uploaded_at(review.getUploaded_at())
                .imageUrl(review.getImageUrl())
                .build();
    }
}
