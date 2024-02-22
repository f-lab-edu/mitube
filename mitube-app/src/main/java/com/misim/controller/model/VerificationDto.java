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

    @Override
    public void check() {

        if (token == null) {
            throw new MitubeException(MitubeErrorCode.INVALID_CODE);
        }

        if (!(!token.isEmpty() && token.length() <= 6)) {
            throw new MitubeException(MitubeErrorCode.INVALID_CODE);
        }

        Validator.validateCode(token);
    }
}
