package com.cotato.homecook.service;

import com.cotato.homecook.domain.entity.Menu;
import com.cotato.homecook.domain.entity.OrderHistory;
import com.cotato.homecook.domain.entity.Shop;
import com.cotato.homecook.exception.AppException;
import com.cotato.homecook.exception.ErrorCode;
import com.cotato.homecook.repository.MenuRepository;
import com.cotato.homecook.repository.OrderHistoryRepository;
import com.cotato.homecook.repository.ShopRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ValidateService {
    private final OrderHistoryRepository orderHistoryRepository;
    private final ShopRepository shopRepository;
    private final MenuRepository menuRepository;

    public OrderHistory findOrderHistoryById(Long orderHistoryId) {
        return orderHistoryRepository.findById(orderHistoryId)
                .orElseThrow(() -> new AppException(ErrorCode.ORDER_HISTORY_NOT_FOUND));
    }

    public void checkDuplicateReview(OrderHistory orderHistory) {
        if (orderHistory.getReview() != null) {
            throw new AppException(ErrorCode.REVIEW_ALREADY_EXIST);
        }
    }

    public Shop validateShop(Long shopId){
        return shopRepository.findById(shopId)
                .orElseThrow(()-> new AppException(ErrorCode.SHOP_NOT_FOUND));
    }

    public Menu validateMenu(Long menuId){
        return menuRepository.findById(menuId).orElseThrow(()-> new AppException(ErrorCode.MENU_NOT_FOUND));
    }
}
