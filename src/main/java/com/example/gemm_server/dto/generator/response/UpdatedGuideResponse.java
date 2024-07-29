package com.example.gemm_server.dto.generator.response;

import io.swagger.v3.oas.annotations.media.Schema;

public record UpdatedGuideResponse(
        @Schema(description = "수정하여 생성한 내용")
        String content
) {
}
