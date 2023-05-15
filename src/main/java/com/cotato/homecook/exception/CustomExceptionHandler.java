package com.cotato.homecook.exception;

import com.cotato.homecook.domain.dto.ApiResponse;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class CustomExceptionHandler {
        @ExceptionHandler(CustomException.class)
        public ApiResponse<?> handleCustomException(CustomException e) {
            return ApiResponse.createError(e.getErrorCode().getHttpStatus(),e.getErrorCode().getMessage());
    }
}
