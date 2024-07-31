package com.example.gemm_server.dto.generator.response;

import io.swagger.v3.oas.annotations.media.Schema;

public record UpdatedCutoutResponse(
        @Schema(description = "수정된 컷/도안 정보")
        CommentedCutoutResponse cutout,
        @Schema(description = "잔여 젬")
        int gem
) {
}
