package com.cotato.homecook.service;

import com.cotato.homecook.domain.dto.auth.UserDto;
import com.cotato.homecook.domain.dto.order.OrderHistoryInfoResponse;
import com.cotato.homecook.domain.entity.*;
import com.cotato.homecook.enums.Role;
import com.cotato.homecook.exception.AppException;
import com.cotato.homecook.exception.ErrorCode;
import com.cotato.homecook.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ValidateService {
    private final OrderHistoryRepository orderHistoryRepository;
    private final ShopRepository shopRepository;
    private final MenuRepository menuRepository;
    private final ReviewRepository reviewRepository;
    private final BookmarkRepository bookmarkRepository;
    private final ReceiptRepository receiptRepository;
    private final CustomerRepository customerRepository;
    private final SellerRepository sellerRepository;

    public void checkDuplicateReceipt(Shop shop) {
        LocalDate today = LocalDate.now();
        Date todayStart = Date.from(today.atStartOfDay(ZoneId.systemDefault()).toInstant());
        Date todayEnd = Date.from(today.atTime(23, 59, 59).atZone(ZoneId.systemDefault()).toInstant());
        if (receiptRepository.existsByUploadedAtBetweenAndShop(todayStart, todayEnd, shop)) {
            throw new AppException(ErrorCode.RECEIPT_ALREADY_EXIST);
        }
    }

    public OrderHistory validateOrderHistory(Long orderHistoryId) {
        return orderHistoryRepository.findById(orderHistoryId)
                .orElseThrow(() -> new AppException(ErrorCode.ORDER_HISTORY_NOT_FOUND));
    }

    public OrderHistoryInfoResponse validateOrderInfoResponse(Long orderHistoryId) {
        List<OrderHistoryInfoResponse> orderHistoryInfoList = orderHistoryRepository.findOrderInfoResponseByOrderHistoryId(orderHistoryId);
        if (orderHistoryInfoList.isEmpty()) {
            throw new AppException(ErrorCode.ORDER_HISTORY_NOT_FOUND);
        }
        {
            return orderHistoryInfoList.get(0);
        }
    }

    public void checkDuplicateReview(OrderHistory orderHistory) {
        if (orderHistory.getReview() != null) {
            throw new AppException(ErrorCode.REVIEW_ALREADY_EXIST);
        }
    }

    public Shop validateShop(Long shopId) {
        return shopRepository.findById(shopId)
                .orElseThrow(() -> new AppException(ErrorCode.SHOP_NOT_FOUND));
    }

    public Menu validateMenu(Long menuId) {
        return menuRepository.findById(menuId).orElseThrow(() -> new AppException(ErrorCode.MENU_NOT_FOUND));
    }

    public Review validateReview(Long reviewId) {
        return reviewRepository.findById(reviewId).orElseThrow(() -> new AppException(ErrorCode.REVIEW_NOT_FOUND));
    }

    public Bookmark validateBookmark(Long bookmarkId) {
        return bookmarkRepository.findById(bookmarkId).orElseThrow(() -> new AppException(ErrorCode.BOOKMARK_NOT_FOUND));
    }

    public Customer validateCustomerByEmail(String email) {
        return customerRepository.findByEmail(email).orElseThrow(()-> new AppException(ErrorCode.USER_NOT_FOUND));
    }

    public Seller validateSellerByEmail(String email) {
        return sellerRepository.findByEmail(email).orElseThrow(()-> new AppException(ErrorCode.USER_NOT_FOUND));
    }

    public UserDto validateUserByEmail(String email) {
        Optional<Customer> customer = customerRepository.findByEmail(email);
        if (customer.isPresent()) {
            return new UserDto(customer.get());
        }
        Optional<Seller> seller = sellerRepository.findByEmail(email);
        if (seller.isPresent()) {
            return new UserDto(seller.get());
        }
        throw new AppException(ErrorCode.USER_NOT_FOUND);
    }

    @Transactional
    public void updateUserRefreshToken(UserDto userDto,String refreshToken) {
        String role = userDto.getRole();
        if (role.equals(Role.ROLE_CUSTOMER.value())) {
            System.out.println("+++++++++++++업데이트");
            Customer customer = validateCustomerByEmail(userDto.getEmail());
            customer.updateRefreshToken(refreshToken);
        } else if (role.equals(Role.ROLE_SELLER.value())) {
            Seller seller = validateSellerByEmail(userDto.getEmail());
            seller.updateRefreshToken(refreshToken);
        } else {
            System.out.println("role = " + role);
        }
    }
}
