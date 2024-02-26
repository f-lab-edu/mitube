package com.misim.controller.model.Request;

import com.misim.controller.model.VerificationDto;
import com.misim.exception.MitubeErrorCode;
import com.misim.exception.MitubeException;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResetPasswordRequest extends VerificationDto {

    @Schema(name = "nickname", description = "닉네임", example = "hongkildong", requiredMode = Schema.RequiredMode.REQUIRED)
    private String nickname;

    @Override
    public void check() {
        super.check();

        if (nickname == null) {
            throw new MitubeException(MitubeErrorCode.INVALID_NICKNAME);
        }
    }
}
