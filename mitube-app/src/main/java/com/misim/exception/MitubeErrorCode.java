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


    NOT_FOUND_TERM(HttpStatus.BAD_REQUEST, "해당 약관을 찾을 수 없습니다.", 20000),
    NOT_AGREE_MANDATORY_TERM(HttpStatus.BAD_REQUEST, "필수 약관에 동의하지 않았습니다.", 20001),
    CHECK_TERMS_UPDATE(HttpStatus.BAD_REQUEST, "약관의 수정 사항을 확인해보세요.", 20002),

    UNKNOWN_EXCEPTION(HttpStatus.INTERNAL_SERVER_ERROR, "알 수 없는 에러가 발생했습니다.", 99999),
    ;

    private final HttpStatus httpStatus;
    private final String message;
    private final int code;
}
