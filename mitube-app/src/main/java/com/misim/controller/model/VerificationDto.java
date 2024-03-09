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

    @Schema(name = "code", description = "코드", example = "123456", requiredMode = Schema.RequiredMode.REQUIRED)
    private String code;

    @Override
    public void check() {

        if (code == null || code.isBlank()) {
            throw new MitubeException(MitubeErrorCode.INVALID_CODE);
        }

        if (!(!code.isEmpty() && code.length() <= 6)) {
            throw new MitubeException(MitubeErrorCode.INVALID_CODE);
        }

        Validator.validateCode(code);
    }
}
