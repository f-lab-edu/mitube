package com.misim.util;

import com.misim.exception.MitubeErrorCode;
import com.misim.exception.MitubeException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validator {
    private static final Pattern emailPattern = Pattern.compile("^(?=.{1,32}@)[A-Za-z0-9_-]+(\\\\.[A-Za-z0-9_-]+)*@[^-][A-Za-z0-9-]+(\\\\.[A-Za-z0-9-]+)*(\\\\.[A-Za-z]{2,})$");
    private static final Pattern passwordPattern = Pattern.compile(
            "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[~@#$%^&+=!])(?=\\S+$).{8,15}$");

    public static void validateEmail(String email) {
        Matcher matcher = emailPattern.matcher(email);

        if (!matcher.matches()) {
            throw new MitubeException(MitubeErrorCode.INVALID_EMAIL);
        }
    }

    public static void validatePassword(String password) {
        Matcher matcher = passwordPattern.matcher(password);

        if (!matcher.matches()) {
            throw new MitubeException(MitubeErrorCode.INVALID_PASSWORD);
        }
    }

    public static void matchPassword(String password, String confirmPassword) {
        if (!password.equals(confirmPassword)) {
            throw new MitubeException(MitubeErrorCode.NOT_MATCH_PASSWORDS);
        }
    }

    public static void validateMandatoryTerms(boolean term1, boolean term2) {
        if (!term1 || !term2) {
            throw new MitubeException(MitubeErrorCode.NOT_AGREE_MANDATORY_TERM);
        }
    }
}
