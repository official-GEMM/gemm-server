package com.example.gemm_server.dto.generator.response;

import io.swagger.v3.oas.annotations.media.Schema;

public record ActivityGuideResponse(
        @Schema(description = "생성한 내용")
        String content
) {
}
