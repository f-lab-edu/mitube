package com.misim.controller.model;

import com.misim.exception.MitubeErrorCode;
import com.misim.exception.MitubeException;
import com.misim.util.Validator;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SmsVerificationDto implements Checker{

    private String code;
    private String phoneNumber;

    @Override
    public void check() {

        if (code == null) {
            throw new MitubeException(MitubeErrorCode.INVALID_CODE);
        }

        if (phoneNumber == null) {
            throw new MitubeException(MitubeErrorCode.INVALID_PHONENUMBER);
        }

        if (!(!code.isEmpty() && code.length() <= 6)) {
            throw new MitubeException(MitubeErrorCode.INVALID_CODE);
        }

        if (!(phoneNumber.length() == 11)) {
            throw new MitubeException(MitubeErrorCode.INVALID_PHONENUMBER);
        }

        Validator.validateCode(code);
        Validator.validatePhoneNumber(phoneNumber);
    }
}
