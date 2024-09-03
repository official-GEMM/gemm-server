package com.example.gemm_server.dto.generator.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

public record CommentGuideRequest(
    @Schema(description = "방법에서 수정할 부분")
    String section,

    @Schema(description = "수정에 적용하고 싶은 내용")
    @NotBlank
    String comment
) {

}
