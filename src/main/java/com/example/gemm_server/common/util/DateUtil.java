package com.example.gemm_server.common.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

public class DateUtil {

    public static LocalDate parseYearMonthDay(String yearMonthDay) {
        try {
            return LocalDate.parse(yearMonthDay);
        } catch (Exception e) {
            throw new IllegalArgumentException(e.getMessage());
        }
    }

    public static boolean isToday(LocalDateTime localDatetime) {
        if (localDatetime == null) {
            return false;
        }
        LocalDate today = LocalDate.now();
        return today.equals(localDatetime.toLocalDate());
    }

    public static Date getExpirationDate(Long expirationTime) {
        Date expiration = new Date();
        expiration.setTime(expiration.getTime() + expirationTime);
        return expiration;
    }
}
