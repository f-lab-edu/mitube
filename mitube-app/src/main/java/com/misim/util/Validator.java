package com.misim.util;

import com.misim.exception.MitubeErrorCode;
import com.misim.exception.MitubeException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validator {
    private static final Pattern emailPattern = Pattern.compile("(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])");
    private static final Pattern passwordPattern = Pattern.compile(
            "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,15}$");

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
