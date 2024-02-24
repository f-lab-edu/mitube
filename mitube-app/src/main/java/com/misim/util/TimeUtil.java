package com.misim.util;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.function.Function;

public class TimeUtil {

    public static LocalDateTime getNow() {
        return LocalDateTime.now(ZoneId.of("Seoul"));
    }
}
