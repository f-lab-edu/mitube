package com.misim.exception;

import org.springframework.core.annotation.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @Order(value = 1)
    @ExceptionHandler(MitubeException.class)
    public ResponseEntity<CommonResponse<?>> handleMitubeException(MitubeException e) {

        e.fillInStackTrace();

        CommonResponse<?> commonResponse = new CommonResponse<>(e.getErrorCode().getCode(), e.getErrorCode().getMessage(), null);
        return ResponseEntity.status(e.getErrorCode().getHttpStatus()).body(commonResponse);
    }

    @Order(value = 2)
    @ExceptionHandler(Exception.class)
    public ResponseEntity<CommonResponse<?>> handleUnknownException(Exception e) {

        e.fillInStackTrace();

        CommonResponse<?> commonResponse = new CommonResponse<>(MitubeErrorCode.UNKNOWN_EXCEPTION.getCode(), MitubeErrorCode.UNKNOWN_EXCEPTION.getMessage(), null);
        return ResponseEntity.status(MitubeErrorCode.UNKNOWN_EXCEPTION.getHttpStatus()).body(commonResponse);
    }
}
