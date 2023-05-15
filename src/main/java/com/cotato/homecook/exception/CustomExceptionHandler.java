package com.cotato.homecook.exception;

import com.cotato.homecook.domain.dto.ApiResponse;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class CustomExceptionHandler {
    @ExceptionHandler(AppException.class)
    public ApiResponse<?> handleCustomException(AppException e) {
        return ApiResponse.createError(e.getErrorCode().getHttpStatus(), e.getErrorCode().getMessage());
    }

    @ExceptionHandler(ImageException.class)
    public ApiResponse<?> handleS3Exception(ImageException e) {
        return ApiResponse.createError(e.getErrorCode().getHttpStatus(), e.getErrorCode().getMessage());
    }
}
