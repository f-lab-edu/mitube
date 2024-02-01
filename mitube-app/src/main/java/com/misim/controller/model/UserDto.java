package com.misim.controller.model;

import com.misim.exception.MitubeErrorCode;
import com.misim.exception.MitubeException;
import com.misim.util.Validator;
import lombok.*;

@Getter
@Setter
public class UserDto implements Checker{

    private String email;
    private String password;
    private String confirmPassword;
    private String nickname;
    private String phoneNumber;
    private boolean agreeMandatoryTerm1;
    private boolean agreeMandatoryTerm2;
    private boolean agreeOptionalTerm1;
    private boolean agreeOptionalTerm2;

    @Override
    public void check() {

        // null 체크
        if (email == null) {
            throw new MitubeException(MitubeErrorCode.INVALID_EMAIL);
        }

        if (password == null) {
            throw new MitubeException(MitubeErrorCode.INVALID_PASSWORD);
        }

        if (confirmPassword == null) {
            throw new MitubeException(MitubeErrorCode.INVALID_CONFIRM_PASSWORD);
        }

        if (nickname == null) {
            throw new MitubeException(MitubeErrorCode.INVALID_NICKNAME);
        }

        // 사이즈 체크
        if (!(!email.isEmpty() && email.length() <= 32)) {
            throw new MitubeException(MitubeErrorCode.INVALID_EMAIL);
        }

        if (!(password.length() >= 8 && password.length() <= 15)) {
            throw new MitubeException(MitubeErrorCode.INVALID_PASSWORD);
        }

        if (!(confirmPassword.length() >= 8 && confirmPassword.length() <= 15)) {
            throw new MitubeException(MitubeErrorCode.INVALID_CONFIRM_PASSWORD);
        }

        if (!(!nickname.isEmpty() && nickname.length() <= 20)) {
            throw new MitubeException(MitubeErrorCode.INVALID_NICKNAME);
        }

        // 패턴 체크
        Validator.validateEmail(email);
        Validator.validatePassword(password);
        Validator.validatePassword(confirmPassword);
        Validator.matchPassword(password, confirmPassword);
        Validator.validateMandatoryTerms(agreeMandatoryTerm1, agreeMandatoryTerm2);
    }
}
