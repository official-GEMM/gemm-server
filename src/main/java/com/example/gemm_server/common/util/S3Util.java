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
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.example.gemm_server.exception.GeneratorException;
import jakarta.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
@RequiredArgsConstructor
public class S3Util {

  private final AmazonS3 amazonS3;
  @Value("${cloud.aws.s3.bucket.name}")
  private String bucketName;
  @Value("${cloud.aws.s3.bucket.expiration-time}")
  private Long expirationTime;

  private static AmazonS3 staticAmazonS3;
  private static String staticBucketName;
  private static Long staticExpirationTime;

  @PostConstruct
  public void init() {
    staticAmazonS3 = this.amazonS3;
    staticBucketName = this.bucketName;
    staticExpirationTime = this.expirationTime;
  }

  public static String uploadFile(File file, String fileName, String saveDirectoryPath) {
    try {
      staticAmazonS3.putObject(staticBucketName, saveDirectoryPath + fileName, file);
      file.delete();
      return saveDirectoryPath + fileName;
    } catch (SdkClientException e) {
      throw new GeneratorException(FAILED_TO_UPLOAD_FILE);
    }
  }

  public static String uploadFile(MultipartFile multipartFile, String fileName,
      String saveDirectoryPath) {
    try {
      ObjectMetadata metadata = new ObjectMetadata();
      metadata.setContentLength(multipartFile.getSize());
      metadata.setContentType(multipartFile.getContentType());

      String s3Key = saveDirectoryPath + fileName;
      PutObjectRequest putObjectRequest = new PutObjectRequest(staticBucketName, s3Key,
          multipartFile.getInputStream(), metadata);
      staticAmazonS3.putObject(putObjectRequest);

      return s3Key;
    } catch (SdkClientException e) {
      throw new GeneratorException(FAILED_TO_UPLOAD_FILE);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  public static InputStream downloadFile(String filePath) {
    try {
      S3Object s3Object = staticAmazonS3.getObject(staticBucketName, filePath);
      return s3Object.getObjectContent();
    } catch (SdkClientException e) {
      throw new GeneratorException(FAILED_TO_DOWNLOAD_FILE);
    }
  }

  public static String copyFile(String fileName, String directoryPath) {
    try {
      if (staticAmazonS3.doesObjectExist(staticBucketName, directoryPath + fileName)) {
        String savePath = directoryPath.substring(5) + fileName;
        staticAmazonS3.copyObject(staticBucketName, directoryPath + fileName, staticBucketName,
            savePath);
        return savePath;
      } else {
        return null;
      }
    } catch (SdkClientException e) {
      throw new GeneratorException(FAILED_TO_COPY_FILE);
    }
  }

  public static String getFileUrl(String filePath) {
    try {
      GeneratePresignedUrlRequest presignedUrlRequest = new GeneratePresignedUrlRequest(
          staticBucketName, filePath)
          .withMethod(HttpMethod.GET)
          .withExpiration(DateUtil.getExpirationDate(staticExpirationTime));
      presignedUrlRequest.addRequestParameter(
          Headers.S3_CANNED_ACL,
          CannedAccessControlList.PublicRead.toString()
      );

      return staticAmazonS3.generatePresignedUrl(presignedUrlRequest).toString();
    } catch (SdkClientException e) {
      throw new GeneratorException(FAILED_TO_GENERATE_PRESIGNED_URL);
    }
  }
}
