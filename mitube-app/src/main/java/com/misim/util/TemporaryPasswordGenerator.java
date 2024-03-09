package com.misim.util;

import java.security.SecureRandom;

public class TemporaryPasswordGenerator {

    public static String generateRandomPassword() {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789@$!%*?&";
        int minLength = 8;
        int maxLength = 15;

        SecureRandom random = new SecureRandom();
        StringBuilder password = new StringBuilder();

        // 최소 길이에서 최대 길이까지 랜덤한 길이를 선택
        int length = random.nextInt(maxLength - minLength + 1) + minLength;

        // 소문자, 대문자, 숫자, 특수문자를 하나씩 포함
        password.append(getRandomChar("abcdefghijklmnopqrstuvwxyz", random));
        password.append(getRandomChar("ABCDEFGHIJKLMNOPQRSTUVWXYZ", random));
        password.append(getRandomChar("0123456789", random));
        password.append(getRandomChar("@$!%*?&", random));

        // 나머지 길이만큼 랜덤한 문자를 추가
        for (int i = 4; i < length; i++) {
            int index = random.nextInt(characters.length());
            password.append(characters.charAt(index));
        }

        // 생성된 임시 비밀번호를 랜덤하게 섞음
        return shuffleString(password.toString(), random);
    }

    private static char getRandomChar(String characterSet, SecureRandom random) {
        int index = random.nextInt(characterSet.length());
        return characterSet.charAt(index);
    }

    private static String shuffleString(String input, SecureRandom random) {
        char[] characters = input.toCharArray();
        for (int i = characters.length - 1; i > 0; i--) {
            int j = random.nextInt(i + 1);
            char temp = characters[i];
            characters[i] = characters[j];
            characters[j] = temp;
        }
        return new String(characters);
    }
}
