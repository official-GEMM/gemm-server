package com.example.gemm_server.dto.generator.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CommentRequest(
        @Schema(description = "방법에서 수정할 부분")
        @NotNull
        String section,
        @Schema(description = "수정에 적용하고 싶은 내용")
        @NotNull @NotBlank
        String comment
) {
}
