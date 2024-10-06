package com.example.gemm_server.common.util;

public class FileUtil {

  public static String generateFileName(String originalFilename) {
    return UUIDUtil.generateRandomUUID() + "_" + originalFilename;
  }

  public static String getFileNameWithNoExtension(String fileName) {
    return fileName.substring(0, fileName.indexOf('.'));
  }

  protected static String getFileExtension(String fileName) {
    return fileName.substring(fileName.lastIndexOf("."));
  }

  public static String getFileNameFromPresignedUrl(String presignedUrl) {
    int queryIndex = presignedUrl.indexOf('?');
    return presignedUrl.substring(presignedUrl.lastIndexOf('/', queryIndex) + 1, queryIndex);
  }
}
