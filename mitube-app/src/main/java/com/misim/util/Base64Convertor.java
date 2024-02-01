package com.misim.util;

import java.util.Base64;

public class Base64Convertor {
    public static String encode(String key) {
        byte[] originalBytes = key.getBytes();

        return Base64.getEncoder().encodeToString(originalBytes);
    }
}
