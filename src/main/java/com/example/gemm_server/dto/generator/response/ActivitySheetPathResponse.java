package com.example.gemm_server.dto.generator.response;

import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.core.io.UrlResource;

public record ActivitySheetPathResponse(
        @Schema(description = "활동지 썸네일 주소")
        UrlResource thumbnailPath,
        @Schema(description = "파일 주소")
        UrlResource filePath
) {
}
