package com.misim.controller.model;

import com.misim.exception.MitubeErrorCode;
import com.misim.exception.MitubeException;
import com.misim.util.Validator;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(description = "인증 관련 DTO")
public class VerificationDto implements Checker{

    @Schema(name = "token", description = "토큰", example = "AIHR==", requiredMode = Schema.RequiredMode.REQUIRED)
    private String token;

    @Schema(name = "nickname", description = "닉네임", example = "hongkildong", requiredMode = Schema.RequiredMode.REQUIRED)
    private String nickname;

    @Schema(name = "phoneNumber", description = "전화번호", example = "01012345678==", requiredMode = Schema.RequiredMode.REQUIRED)
    private String phoneNumber;

    @Override
    public void check() {

        if (token == null) {
            throw new MitubeException(MitubeErrorCode.INVALID_CODE);
        }

        if (nickname == null) {
            throw new MitubeException(MitubeErrorCode.INVALID_NICKNAME);
        }

        if (phoneNumber == null) {
            throw new MitubeException(MitubeErrorCode.INVALID_PHONENUMBER);
        }

        if (!(!token.isEmpty() && token.length() <= 6)) {
            throw new MitubeException(MitubeErrorCode.INVALID_CODE);
        }

        if (!(phoneNumber.length() == 11)) {
            throw new MitubeException(MitubeErrorCode.INVALID_PHONENUMBER);
        }

        Validator.validateCode(token);
        Validator.validatePhoneNumber(phoneNumber);
    }
}
