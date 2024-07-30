package com.example.gemm_server.dto.generator.response;

import io.swagger.v3.oas.annotations.media.Schema;

public record UpdatedPptResponse(
        @Schema(description = "수정된 PPT 정보")
        CommentedPptResponse ppt,
        @Schema(description = "잔여 잼")
        int gem
) {
}
