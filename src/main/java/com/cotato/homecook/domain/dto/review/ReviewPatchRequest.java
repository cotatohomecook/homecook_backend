package com.cotato.homecook.domain.dto.review;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReviewPatchRequest {
    String content;
    private double rating;
}
