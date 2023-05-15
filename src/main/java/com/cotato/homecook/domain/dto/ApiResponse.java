package com.cotato.homecook.domain.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class ApiResponse<T> {
    private static final String SUCCESS_RESULT = "success";
    private static final String FAIL_RESULT = "fail";
    private static final String ERROR_RESULT = "error";

    private String result;
    private HttpStatus httpStatus;
    private String message;
    private T data;

    public static <T> ApiResponse<T> createSuccess(T data) {
        return new ApiResponse<>(SUCCESS_RESULT, HttpStatus.OK, null, data);
    }

    public static ApiResponse<?> createSuccessWithNoData(String message) {
        return new ApiResponse<>(SUCCESS_RESULT, HttpStatus.OK, message, null);
    }

    public static ApiResponse<?> createError(HttpStatus httpStatus, String message) {
        return new ApiResponse<>(ERROR_RESULT, httpStatus, message, ERROR_RESULT);
    }
}
