package com.example.gemm_server.common.util;

public class NumberUtil {

  public static String getRandomNumber(int length) {
    StringBuilder sb = new StringBuilder();
    for (int i = 0; i < length; i++) {
      int number = (int) (Math.random() * 10);
      sb.append(number);
    }
    return sb.toString();
  }

  public static Float roundToDecimalPlaces(float value, int decimalPlaces) {
    if (decimalPlaces < 0) {
      throw new IllegalArgumentException("Decimal places must be non-negative");
    }
    float factor = (float) Math.pow(10, decimalPlaces);
    return Math.round(value * factor) / factor;
  }
}
