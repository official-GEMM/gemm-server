package com.example.gemm_server.common.util;

import static com.example.gemm_server.common.code.error.GeneratorErrorCode.*;

import com.amazonaws.HttpMethod;
import com.amazonaws.SdkClientException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.Headers;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.example.gemm_server.exception.GeneratorException;
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
    try {
      amazonS3.putObject(bucketName, savePath + fileName, file);
      file.delete();
      return savePath + fileName;
    } catch (SdkClientException e) {
      throw new GeneratorException(FAILED_TO_UPLOAD_FILE);
    }
  }

  public InputStream downloadFile(String fileName) {
    try {
      S3Object s3Object = amazonS3.getObject(bucketName, fileName);
      return s3Object.getObjectContent();
    } catch (SdkClientException e) {
      throw new GeneratorException(FAILED_TO_DOWNLOAD_FILE);
    }
  }

  public String copyFile(String fileName, String filePath) {
    try {
      if (amazonS3.doesObjectExist(bucketName, filePath + fileName)) {
        String savePath = filePath.substring(5) + fileName;
        amazonS3.copyObject(bucketName, filePath + fileName, bucketName, savePath);
        return savePath;
      } else {
        return null;
      }
    } catch (SdkClientException e) {
      throw new GeneratorException(FAILED_TO_COPY_FILE);
    }
  }

  public String getFileUrl(String fileName) {
    try {
      GeneratePresignedUrlRequest presignedUrlRequest = new GeneratePresignedUrlRequest(
          bucketName, fileName)
          .withMethod(HttpMethod.GET)
          .withExpiration(DateUtil.getExpirationDate(1000 * 60 * 2L));
      presignedUrlRequest.addRequestParameter(
          Headers.S3_CANNED_ACL,
          CannedAccessControlList.PublicRead.toString()
      );

      return amazonS3.generatePresignedUrl(presignedUrlRequest).toString();
    } catch (SdkClientException e) {
      throw new GeneratorException(FAILED_TO_GENERATE_PRESIGNED_URL);
    }
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
