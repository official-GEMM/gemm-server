package com.example.gemm_server.common.util;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class DateUtil {

  public static LocalDate parseYearMonthDay(String yearMonthDay) {
    try {
      return LocalDate.parse(yearMonthDay);
    } catch (Exception e) {
      throw new IllegalArgumentException(e.getMessage());
    }
  }

  public static boolean isToday(LocalDateTime localDatetime) {
    LocalDate today = LocalDate.now();
    return today.equals(localDatetime.toLocalDate());
  }
}
