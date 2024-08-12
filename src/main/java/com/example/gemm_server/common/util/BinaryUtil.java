package com.example.gemm_server.common.util;

public class BinaryUtil {

  public static String parseBinaryString(Short value) {
    String binaryString = Integer.toBinaryString(Short.toUnsignedInt(value));
    return String.format("%3s", binaryString).replace(' ', '0');
  }
}
