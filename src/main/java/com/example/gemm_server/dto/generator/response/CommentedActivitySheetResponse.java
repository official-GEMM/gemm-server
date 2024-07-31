package com.example.gemm_server.dto.generator.response;

import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.core.io.UrlResource;

public record CommentedActivitySheetResponse(
        @Schema(description = "수정된 활동지 썸네일 주소")
        UrlResource thumbnailPath,
        @Schema(description = "수정된 활동지 파일 주소")
        UrlResource filePath
) {
}
