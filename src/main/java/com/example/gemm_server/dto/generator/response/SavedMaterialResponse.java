package com.example.gemm_server.dto.generator.response;

import io.swagger.v3.oas.annotations.media.Schema;

public record SavedMaterialResponse(
        @Schema(description = "저장한 활동 자료 아이디")
        Long generationId
) {
}
