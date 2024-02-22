package com.misim.controller.model.Request;

import com.misim.controller.model.VerificationDto;
import com.misim.exception.MitubeErrorCode;
import com.misim.exception.MitubeException;
import com.misim.util.Validator;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
public class VerifySMSRequest extends VerificationDto {

    @Schema(name = "phoneNumber", description = "전화번호", example = "01012345678==", requiredMode = Schema.RequiredMode.REQUIRED)
    private String phoneNumber;

    @Override
    public void check() {
        super.check();

        if (this.phoneNumber == null) {
            throw new MitubeException(MitubeErrorCode.INVALID_PHONENUMBER);
        }

        if (!(this.phoneNumber.length() == 11)) {
            throw new MitubeException(MitubeErrorCode.INVALID_PHONENUMBER);
        }

        Validator.validatePhoneNumber(this.phoneNumber);
    }
}
