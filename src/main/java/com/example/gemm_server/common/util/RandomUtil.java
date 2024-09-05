package com.example.gemm_server.common.util;

public class RandomUtil {

  public static String getRandomNumber(int length) {
    StringBuilder sb = new StringBuilder();
    for (int i = 0; i < length; i++) {
      int number = (int) (Math.random() * 10);
      sb.append(number);
    }
    return sb.toString();
  }
}
