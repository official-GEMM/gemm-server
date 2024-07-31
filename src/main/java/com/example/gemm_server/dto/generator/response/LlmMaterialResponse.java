package com.example.gemm_server.dto.generator.response;

import org.springframework.core.io.UrlResource;

public record LlmMaterialResponse(
        UrlResource ppt,
        UrlResource activitySheet,
        UrlResource cutout
) {
}
