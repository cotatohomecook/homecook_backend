package com.cotato.homecook.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
public enum ErrorCode {
    REVIEW_ALREADY_EXIST(HttpStatus.CONFLICT, "해당 주문 기록에 대한 리뷰가 이미 존재합니다."),
    RECEIPT_ALREADY_EXIST(HttpStatus.CONFLICT,"오늘 이미 영수증을 업로드하였습니다."),
    ORDER_HISTORY_NOT_FOUND(HttpStatus.NOT_FOUND, "주문 기록이 없습니다."),
    IMAGE_PROCESSING_FAIL(HttpStatus.INTERNAL_SERVER_ERROR,"이미지 처리에 실패했습니다."), 
    SHOP_NOT_FOUND(HttpStatus.NOT_FOUND, "존재하지 않는 상점입니다."),
    MENU_NOT_FOUND(HttpStatus.NOT_FOUND, "존재하지 않는 메뉴입니다."),
    REVIEW_NOT_FOUND(HttpStatus.NOT_FOUND, "존재하지 않는 리뷰입니다."),
    BOOKMARK_NOT_FOUND(HttpStatus.NOT_FOUND, "존재하지 않는 북마크입니다."),
    CUSTOMER_NOT_FOUND(HttpStatus.NOT_FOUND, "존재하지 않는 구매자입니다."),
    RECEIPT_NOT_FOUND(HttpStatus.NOT_FOUND, "존재하지 않는 영수증입니다."),
    SELLER_NOT_FOUND(HttpStatus.NOT_FOUND,"존재하지 않는 판매자입니다."),
    USER_NOT_FOUND(HttpStatus.NOT_FOUND,"존재하지 않는 사용자입니다.");
    private final HttpStatus httpStatus;
    private final String message;
    }
