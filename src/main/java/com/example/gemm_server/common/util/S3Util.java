package com.example.gemm_server.common.util;

import com.amazonaws.HttpMethod;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.Headers;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
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
        System.out.println(file.getName());
        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentType(file.getContentType());
        objectMetadata.setContentLength(file.getSize());

        try {
            amazonS3.putObject(bucketName, "testfile", file.getInputStream(),
                objectMetadata);
            return generatePresignedUrl("testfile");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
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
}
