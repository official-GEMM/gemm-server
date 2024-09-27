package com.example.gemm_server.common.util;

import static com.example.gemm_server.common.code.error.GeneratorErrorCode.FAILED_TO_COPY_FILE;
import static com.example.gemm_server.common.code.error.GeneratorErrorCode.FAILED_TO_DOWNLOAD_FILE;
import static com.example.gemm_server.common.code.error.GeneratorErrorCode.FAILED_TO_GENERATE_PRESIGNED_URL;
import static com.example.gemm_server.common.code.error.GeneratorErrorCode.FAILED_TO_UPLOAD_FILE;

import com.amazonaws.HttpMethod;
import com.amazonaws.SdkClientException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.Headers;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.example.gemm_server.exception.GeneratorException;
import jakarta.annotation.PostConstruct;
import java.io.File;
import java.io.InputStream;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class S3Util {

  private final AmazonS3 amazonS3;
  @Value("${cloud.aws.s3.bucket.name}")
  private String bucketName;

  private static AmazonS3 staticAmazonS3;
  private static String staticBucketName;

  @PostConstruct
  public void init() {
    staticAmazonS3 = this.amazonS3;
    staticBucketName = this.bucketName;
  }

  public static String uploadFile(File file, String fileName, String savePath) {
    try {
      staticAmazonS3.putObject(staticBucketName, savePath + fileName, file);
      file.delete();
      return savePath + fileName;
    } catch (SdkClientException e) {
      throw new GeneratorException(FAILED_TO_UPLOAD_FILE);
    }
  }

  public static InputStream downloadFile(String fileName) {
    try {
      S3Object s3Object = staticAmazonS3.getObject(staticBucketName, fileName);
      return s3Object.getObjectContent();
    } catch (SdkClientException e) {
      throw new GeneratorException(FAILED_TO_DOWNLOAD_FILE);
    }
  }

  public static String copyFile(String fileName, String filePath) {
    try {
      if (staticAmazonS3.doesObjectExist(staticBucketName, filePath + fileName)) {
        String savePath = filePath.substring(5) + fileName;
        staticAmazonS3.copyObject(staticBucketName, filePath + fileName, staticBucketName,
            savePath);
        return savePath;
      } else {
        return null;
      }
    } catch (SdkClientException e) {
      throw new GeneratorException(FAILED_TO_COPY_FILE);
    }
  }

  public static String getFileUrl(String fileName) {
    try {
      GeneratePresignedUrlRequest presignedUrlRequest = new GeneratePresignedUrlRequest(
          staticBucketName, fileName)
          .withMethod(HttpMethod.GET)
          .withExpiration(DateUtil.getExpirationDate(1000 * 60 * 2L));
      presignedUrlRequest.addRequestParameter(
          Headers.S3_CANNED_ACL,
          CannedAccessControlList.PublicRead.toString()
      );

      return staticAmazonS3.generatePresignedUrl(presignedUrlRequest).toString();
    } catch (SdkClientException e) {
      throw new GeneratorException(FAILED_TO_GENERATE_PRESIGNED_URL);
    }
  }

  public static String getFileNameFromPresignedUrl(String presignedUrl) {
    return presignedUrl.substring(presignedUrl.lastIndexOf('/') + 1, presignedUrl.indexOf('?'));
  }

  protected static String getFileExtension(String fileName) {
    return fileName.substring(fileName.lastIndexOf("."));
  }
}
