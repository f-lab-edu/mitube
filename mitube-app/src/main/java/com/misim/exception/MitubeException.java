package com.misim.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class MitubeException extends RuntimeException {
    private final MitubeErrorCode errorCode;
}
