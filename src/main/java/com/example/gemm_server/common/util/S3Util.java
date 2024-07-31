package com.example.gemm_server.common.util;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.util.IOUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;

@Component
@RequiredArgsConstructor
public class S3Util {
    private final AmazonS3 amazonS3;
    @Value("${cloud.aws.s3.bucket.name}")
    private String bucketName;
    private final String tempFilePath = "../";

    public File downloadFile(String filePath) {
        S3Object s3FileObject = amazonS3.getObject(bucketName, filePath);
        try {
            byte[] s3fileBytes = IOUtils.toByteArray(s3FileObject.getObjectContent());
            return Files.write(Path.of(tempFilePath), s3fileBytes).toFile();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
