package com.cotato.homecook.service;

import com.cotato.homecook.domain.entity.OrderHistory;
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
                // TODO : 커스텀 예외 클래스 만들기
                .orElseThrow(() -> new RuntimeException("order history not exist"));
    }

    public void checkDuplicateReview(OrderHistory orderHistory) {
        if (orderHistory.getReview() != null) {
            throw new IllegalStateException("review already exists");
        }
    }
}
