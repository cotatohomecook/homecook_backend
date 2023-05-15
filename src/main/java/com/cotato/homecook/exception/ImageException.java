package com.cotato.homecook.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.io.IOException;
@AllArgsConstructor
@Getter
public class ImageException extends IOException {
    ErrorCode errorCode;
}
