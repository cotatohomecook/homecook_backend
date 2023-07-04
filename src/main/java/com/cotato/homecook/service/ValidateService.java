package com.cotato.homecook.service;

import com.cotato.homecook.domain.dto.order.OrderHistoryInfoResponse;
import com.cotato.homecook.domain.entity.*;
import com.cotato.homecook.exception.AppException;
import com.cotato.homecook.exception.ErrorCode;
import com.cotato.homecook.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ValidateService {
    private final OrderHistoryRepository orderHistoryRepository;
    private final ShopRepository shopRepository;
    private final MenuRepository menuRepository;
    private final ReviewRepository reviewRepository;
    private final BookmarkRepository bookmarkRepository;

    public OrderHistory findOrderHistoryById(Long orderHistoryId) {
        return orderHistoryRepository.findById(orderHistoryId)
                .orElseThrow(() -> new AppException(ErrorCode.ORDER_HISTORY_NOT_FOUND));
    }

    public OrderHistoryInfoResponse validateOrderInfoResponse(Long orderHistoryId) {
        List<OrderHistoryInfoResponse> orderHistoryInfoList = orderHistoryRepository.findOrderInfoResponseByOrderHistoryId(orderHistoryId);
        if(orderHistoryInfoList.isEmpty()){
            throw new AppException(ErrorCode.ORDER_HISTORY_NOT_FOUND);
        } {
            return orderHistoryInfoList.get(0);
        }
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
    public Review validateReview(Long reviewId){
        return reviewRepository.findById(reviewId).orElseThrow(()-> new AppException(ErrorCode.REVIEW_NOT_FOUND));
    }
    public Bookmark validateBookmark(Long bookmarkId){
        return bookmarkRepository.findById(bookmarkId).orElseThrow(()-> new AppException(ErrorCode.BOOKMARK_NOT_FOUND));
    }
}
