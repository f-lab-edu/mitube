package com.misim.util;

public class SecondaryIndexConvertor {
    public static String encode(Long userId, Long videoId) {
        return userId + "mitube" + videoId;
    }
}
