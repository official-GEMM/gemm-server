package com.example.gemm_server.dto.generator.response;

import io.swagger.v3.oas.annotations.media.Schema;

public record CommentedCutoutResponse(
        @Schema(description = "수정한 컷/도안 썸네일 주소")
        String thumbnailPath,

        @Schema(description = "수정한 컷/도안 파일 주소")
        String filePath
) {
}
