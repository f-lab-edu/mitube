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

    EXIST_NICKNAME(HttpStatus.CONFLICT, "이미 가입된 닉네임입니다.", 10100),
    EXIST_EMAIL(HttpStatus.CONFLICT, "이미 가입된 이메일입니다.", 10101),

    NOT_FOUND_TOKEN(HttpStatus.BAD_REQUEST, "해당 토큰을 찾을 수 없습니다.", 10200),
    NOT_FOUND_SMS_TOKEN(HttpStatus.BAD_REQUEST, "해당 SMS 토큰을 찾을 수 없습니다.", 10201),
    EXPIRED_TOKEN(HttpStatus.BAD_REQUEST, "해당 토큰이 만료되었습니다.", 10202),
    INVALID_SMS_TOKEN(HttpStatus.BAD_REQUEST, "SMS 토큰 형식에 맞지 않습니다.", 10203),
    NOT_VERIFIED_SMS_TOKEN(HttpStatus.BAD_REQUEST, "토큰이 인증되지 않았습니다.", 10204),
    USED_SMS_TOKEN(HttpStatus.BAD_REQUEST, "이미 사용된 토큰입니다.", 10205),

    NOT_FOUND_TERM(HttpStatus.BAD_REQUEST, "해당 약관을 찾을 수 없습니다.", 10300),
    NOT_AGREE_REQUIRED_TERM(HttpStatus.BAD_REQUEST, "필수 약관에 동의하지 않았습니다.", 10301),
    CHECK_TERMS_UPDATE(HttpStatus.BAD_REQUEST, "약관의 수정 사항을 확인해보세요.", 10302),
    NOT_MATCH_TERM_AND_TERM_AGREEMENT(HttpStatus.BAD_REQUEST, "약관과 약관 동의 사항이 일치하지 않습니다.", 10303),

    NOT_FOUND_CODE(HttpStatus.BAD_REQUEST, "해당 코드를 찾을 수 없습니다.", 10400),
    INVALID_CODE(HttpStatus.BAD_REQUEST, "코드 형식에 맞지 않습니다.", 10401),
    EXPIRED_CODE(HttpStatus.BAD_REQUEST, "해당 코드이 만료되었습니다.", 10402),
    VERIFIED_CODE(HttpStatus.BAD_REQUEST, "해당 코드이 인증되었습니다.", 10403),
    MAX_FAILURES(HttpStatus.BAD_REQUEST, "최대 인증 실패 횟수를 넘었습니다.", 10404),
    NOT_MATCH_CODE(HttpStatus.BAD_REQUEST, "잘못된 코드입니다.", 10405),

    INVALID_PHONENUMBER(HttpStatus.BAD_REQUEST, "전화번호 형식에 맞지 않습니다.", 10500),

    EMPTY_FILE(HttpStatus.BAD_REQUEST, "빈 파일입니다.", 10600),
    NOT_VIDEO_FILE(HttpStatus.BAD_REQUEST, "비디오 파일이 아닙니다.", 10601),
    NOT_CREATED_DIR(HttpStatus.BAD_REQUEST, "디렉토리 생성에 실패했습니다.", 10602),
    NOT_CREATED_FILE(HttpStatus.BAD_REQUEST, "파일 생성에 실패했습니다.", 10603),
    INVALID_TITLE(HttpStatus.BAD_REQUEST, "비디오 타이틀 형식에 맞지 않습니다.", 10604),
    INVALID_DESCRIPTION(HttpStatus.BAD_REQUEST, "비디오 설명 형식에 맞지 않습니다.", 10605),
    INVALID_VIDEO_TOKEN(HttpStatus.BAD_REQUEST, "비디오 토큰 형식에 맞지 않습니다.", 10606),
    NOT_FOUND_VIDEO_FILE(HttpStatus.BAD_REQUEST, "비디오 파일을 찾을 수 없습니다.", 10607),
    NOT_FOUND_USER(HttpStatus.BAD_REQUEST, "유저를 찾을 수 없습니다.", 10608),
    INVALID_CATEGORY(HttpStatus.BAD_REQUEST, "비디오 카테고리 형식에 맞지 않습니다.", 10609),

    INVALID_LOCAL_DATETIME(HttpStatus.BAD_REQUEST, "잘못된 LocalDateTime 형식입니다.", 10700),

    UNKNOWN_EXCEPTION(HttpStatus.INTERNAL_SERVER_ERROR, "알 수 없는 에러가 발생했습니다.", 99999),;

    private final HttpStatus httpStatus;
    private final String message;
    private final int code;
}
