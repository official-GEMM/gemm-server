package com.example.gemm_server.common.util;

import com.amazonaws.services.s3.AmazonS3;
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

    public String uploadFile(MultipartFile materialFile) {
        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentType(materialFile.getContentType());
        objectMetadata.setContentLength(materialFile.getSize());

        try {
            amazonS3.putObject(bucketName, materialFile.getName(), materialFile.getInputStream(), objectMetadata);
            return amazonS3.getUrl(bucketName, materialFile.getName()).toString();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
