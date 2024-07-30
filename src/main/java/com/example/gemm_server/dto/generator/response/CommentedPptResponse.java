package com.example.gemm_server.dto.generator.response;

import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.core.io.UrlResource;

public record CommentedPptResponse(
        @Schema(description = "수정한 PPT 썸네일 주소")
        UrlResource[] thumbnailPaths,
        @Schema(description = "수정한 PPT 파일 주소")
        UrlResource filePath
) {
}
