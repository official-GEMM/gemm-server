package com.example.gemm_server.common.util;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;

public class UUIDUtil {

  public static String createReferralCode() {
    // TODO: 생성한 추천인 코드의 중복 검사
    String uuidString = UUID.randomUUID().toString();
    byte[] uuidStringBytes = uuidString.getBytes(StandardCharsets.UTF_8);
    byte[] hashBytes;

    try {
      MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
      hashBytes = messageDigest.digest(uuidStringBytes);
    } catch (NoSuchAlgorithmException e) {
      throw new RuntimeException(e);
    }

    StringBuilder sb = new StringBuilder();
    for (int j = 0; j < 4; j++) {
      sb.append(String.format("%02X", hashBytes[j]));
    }
    return sb.toString();
  }

  public static String getRandomUUID() {
    return UUID.randomUUID().toString();
  }
}