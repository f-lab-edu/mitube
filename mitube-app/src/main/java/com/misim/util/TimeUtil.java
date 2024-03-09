package com.misim.util;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class TimeUtil {

    public static LocalDateTime getNow() {
        return LocalDateTime.now().atZone(ZoneId.of("Asia/Seoul")).toLocalDateTime();
    }

    public static String formatLocalDateTime(LocalDateTime localDateTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSSSSS");
        return localDateTime.format(formatter);
    }

    public static String formatLocalDateTimeNow() {
        LocalDateTime localDateTime = getNow();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSSSSS");
        return localDateTime.format(formatter);
    }

    public static LocalDateTime parseStringToLocalDateTime(String dateTimeString) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSSSSS");
        return LocalDateTime.parse(dateTimeString, formatter);
    }
}
