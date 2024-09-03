package com.example.gemm_server.dto.generator.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

public record CommentPptRequest(
    @Schema(description = "수정할 슬라이드 번호")
    Integer section,

    @Schema(description = "수정에 적용하고 싶은 내용")
    @NotBlank
    String comment
) {

}
