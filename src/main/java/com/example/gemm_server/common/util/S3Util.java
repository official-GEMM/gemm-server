package com.example.gemm_server.common.util;

import com.amazonaws.HttpMethod;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.Headers;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;
import com.amazonaws.services.s3.model.S3Object;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.*;

@Component
@RequiredArgsConstructor
public class S3Util {

  private final AmazonS3 amazonS3;
  @Value("${cloud.aws.s3.bucket.name}")
  private String bucketName;

  public String uploadFile(File file, String fileName, String savePath) {
    amazonS3.putObject(bucketName, savePath + fileName, file);
    return savePath + fileName;
  }

  public InputStream downloadFile(String fileName) {
    S3Object s3Object = amazonS3.getObject(bucketName, fileName);
    InputStream objectContentStream = s3Object.getObjectContent();
    return objectContentStream;
  }

  public String copyFile(String fileName, String filePath) {
    if (amazonS3.doesObjectExist(bucketName, filePath + fileName)) {
      String savePath = filePath.substring(5) + fileName;
      amazonS3.copyObject(bucketName, filePath + fileName, bucketName, savePath);
      return savePath;
    } else {
      return null;
    }
  }

  public String getFileUrl(String fileName) {
    GeneratePresignedUrlRequest presignedUrlRequest = new GeneratePresignedUrlRequest(
        bucketName, fileName)
        .withMethod(HttpMethod.GET)
        .withExpiration(DateUtil.getExpirationDate(1000 * 60 * 2L));
    presignedUrlRequest.addRequestParameter(
        Headers.S3_CANNED_ACL,
        CannedAccessControlList.PublicRead.toString()
    );

    return amazonS3.generatePresignedUrl(presignedUrlRequest).toString();
  }

  public String getUUIDFileName(String fileName) {
    return UUID.randomUUID().toString() + "." + getFileExtension(fileName);
  }

  public String getFileNameFromPresignedUrl(String presignedUrl) {
    return presignedUrl.substring(presignedUrl.lastIndexOf('/') + 1, presignedUrl.indexOf('?'));
  }

  public String getFileNameFromPresignedUrlWithNoExtension(String presignedUrl) {
    return presignedUrl.substring(presignedUrl.lastIndexOf('/') + 1, presignedUrl.indexOf('.'));
  }

  protected String getFileExtension(String fileName) {
    return fileName.substring(fileName.lastIndexOf("."));
  }
}
