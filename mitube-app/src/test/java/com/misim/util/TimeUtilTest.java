package com.misim.util;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class TimeUtilTest {

    @Test
    void test() {
        LocalDateTime current = TimeUtil.getNow();

        String s = TimeUtil.formatLocalDateTime(current);

        LocalDateTime localDateTime = TimeUtil.parseStringToLocalDateTime(s);

        System.out.println("LocalDateTime current is " + current);
        System.out.println("L -> S current is " + s);
        System.out.println("L -> S -> L current is "+ localDateTime);
        System.out.println(current.isEqual(localDateTime));
        System.out.println("S length is " + s.length());

        String patternToCheck = "2024-02-27T04:18:01.921547900";

        boolean isValid = isValidPattern(patternToCheck);

        if (isValid) {
            System.out.println("Pattern is valid");
        } else {
            System.out.println("Pattern is not valid");
        }
    }

    public static boolean isValidPattern(String input) {
        // 정규표현식 패턴
        String pattern = "^\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}:\\d{2}\\.\\d{9}$";

        // 패턴과 입력 문자열을 비교
        Pattern regex = Pattern.compile(pattern);
        Matcher matcher = regex.matcher(input);

        return matcher.matches();
    }
}