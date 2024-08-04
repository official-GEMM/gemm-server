package com.example.gemm_server.common.util;

import com.amazonaws.HttpMethod;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.Headers;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;

@Component
@RequiredArgsConstructor
public class S3Util {

    private final AmazonS3 amazonS3;
    @Value("${cloud.aws.s3.bucket.name}")
    private String bucketName;

    public String uploadFile(MultipartFile file) {
        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentType(file.getContentType());
        objectMetadata.setContentLength(file.getSize());

        String fileName = getUUIDFileName(file.getOriginalFilename());
        try {
            amazonS3.putObject(bucketName, fileName, file.getInputStream(),
                objectMetadata);
            return fileName;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public String getFileUrl(String fileName) {
        return generatePresignedUrl(fileName);
    }

    protected String generatePresignedUrl(String fileName) {
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

    protected String getFileExtension(String fileName) {
        return fileName.substring(fileName.lastIndexOf("."));
    }

    protected String getUUIDFileName(String fileName) {
        return UUID.randomUUID().toString() + "." + getFileExtension(fileName);
    }
}
