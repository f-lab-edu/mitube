package com.misim.controller.model.Request;

import com.misim.controller.model.VerificationDto;
import com.misim.exception.MitubeErrorCode;
import com.misim.exception.MitubeException;
import com.misim.util.Validator;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VerifySMSRequest extends VerificationDto {

    @Schema(name = "phoneNumber", description = "전화번호", example = "01012345678", requiredMode = Schema.RequiredMode.REQUIRED)
    private String phoneNumber;

    @Schema(name = "requestTime", description = "클라이언트 요청 시각", example = "yyyy-MM-dd HH:mm:ss", requiredMode = Schema.RequiredMode.REQUIRED)
    private String requestTime;


    @Override
    public void check() {

        // requestTime에 대한 검사
        
        if (this.phoneNumber == null) {
            throw new MitubeException(MitubeErrorCode.INVALID_PHONENUMBER);
        }

        if (this.requestTime == null) {
            throw new MitubeException(MitubeErrorCode.INVALID_LOCAL_DATETIME);
        }

        if (!(this.phoneNumber.length() == 11)) {
            throw new MitubeException(MitubeErrorCode.INVALID_PHONENUMBER);
        }

        if (!(this.requestTime.length() == 29)) {
            throw new MitubeException(MitubeErrorCode.INVALID_LOCAL_DATETIME);
        }

        Validator.validatePhoneNumber(this.phoneNumber);
        Validator.validateLocalDateTime(this.requestTime);
    }
}
