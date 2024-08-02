package com.example.gemm_server.dto.generator.response;

import io.swagger.v3.oas.annotations.media.Schema;

public record CommentedPptResponse(
        @Schema(description = "수정한 PPT 썸네일 주소")
        String[] thumbnailPaths,

        @Schema(description = "수정한 PPT 파일 주소")
        String filePath
) {
}
