package com.cotato.homecook.service;

import com.cotato.homecook.domain.entity.OrderHistory;
import com.cotato.homecook.exception.CustomException;
import com.cotato.homecook.exception.ErrorCode;
import com.cotato.homecook.repository.OrderHistoryRepository;
import com.cotato.homecook.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.aspectj.weaver.ast.Or;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ValidateService {
    private final OrderHistoryRepository orderHistoryRepository;

    public OrderHistory findOrderHistoryById(Long orderHistoryId) {
        return orderHistoryRepository.findById(orderHistoryId)
                .orElseThrow(() -> new CustomException(ErrorCode.ORDER_HISTORY_NOT_FOUND));
    }

    public void checkDuplicateReview(OrderHistory orderHistory) {
        if (orderHistory.getReview() != null) {
            throw new CustomException(ErrorCode.REVIEW_ALREADY_EXIST);
        }
    }
}
