package com.example.gemm_server.dto.generator.response;

import io.swagger.v3.oas.annotations.media.Schema;

public record SavedActivityResponse(
        @Schema(description = "저장한 활동 방법 아이디")
        Long storageId
) {
}
