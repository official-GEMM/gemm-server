package com.example.gemm_server.dto.generator.response;

import io.swagger.v3.oas.annotations.media.Schema;
public record ActivitySheetPathResponse(
        @Schema(description = "활동지 썸네일 주소")
        String thumbnailPath,

        @Schema(description = "파일 주소")
        String filePath
) {
}
