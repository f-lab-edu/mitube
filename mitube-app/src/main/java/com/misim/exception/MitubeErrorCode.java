package com.misim.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum MitubeErrorCode implements ErrorCode{

    NOT_MATCH_PASSWORDS(HttpStatus.BAD_REQUEST, "비밀번호와 확인 비밀번호가 일치하지 않습니다.", 10000),
    INVALID_PASSWORD(HttpStatus.BAD_REQUEST, "비밀번호 형식에 맞지 않습니다.", 10001),
    INVALID_CONFIRM_PASSWORD(HttpStatus.BAD_REQUEST, "확인 비밀번호 형식에 맞지 않습니다.", 10002),
    INVALID_EMAIL(HttpStatus.BAD_REQUEST, "이메일 형식에 맞지 않습니다.", 10003),
    INVALID_NICKNAME(HttpStatus.BAD_REQUEST, "닉네임 형식에 맞지 않습니다.", 10004),

    EXIST_NICKNAME(HttpStatus.CONFLICT, "이미 가입된 닉네임입니다.", 10010),

    UNKNOWN_EXCEPTION(HttpStatus.INTERNAL_SERVER_ERROR, "알 수 없는 에러가 발생했습니다.", 99999),
    ;

    private final HttpStatus httpStatus;
    private final String message;
    private final int code;
}
