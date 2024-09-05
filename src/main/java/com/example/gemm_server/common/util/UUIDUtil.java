package com.example.gemm_server.common.util;

import java.util.UUID;

public class UUIDUtil {

  public static String generateCharacterUUID(int length) {
    String uuid = UUID.randomUUID().toString().replace("-", "").toUpperCase();
    return uuid.substring(0, length);
  }

  public static String getRandomUUID() {
    return UUID.randomUUID().toString();
  }
}