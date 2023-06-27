package com.cotato.homecook.domain.dto.order;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class OrderHistorySellerResponse {
    Long orderHistoryId;
    Long menuCount;
    String representativeMenuName;
    double rating;
    @JsonFormat(shape= JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm", timezone="Asia/Seoul")
    LocalDateTime orderedAt;
}
