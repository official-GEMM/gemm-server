package com.example.gemm_server.common.util;

import com.example.gemm_server.common.constant.TimeZone;
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

  public static boolean isToday(LocalDate localDate) {
    if (localDate == null) {
      return false;
    }
    LocalDate today = LocalDate.now(TimeZone.DEFAULT);
    return today.equals(localDate);
  }

  public static boolean isToday(LocalDateTime localDatetime) {
    if (localDatetime == null) {
      return false;
    }
    LocalDate today = LocalDate.now(TimeZone.DEFAULT);
    return today.equals(localDatetime.toLocalDate());
  }

  public static Date getExpirationDate(Long expirationTime) {
    Date expiration = new Date();
    expiration.setTime(expiration.getTime() + expirationTime);
    return expiration;
  }
}
