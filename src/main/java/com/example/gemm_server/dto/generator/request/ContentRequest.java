package com.example.gemm_server.dto.generator.request;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "활동 방법 내용 응답")
public record ContentRequest(
    @Schema(description = "활동 방법 제목")
    String title,
    @Schema(description = "활동 방법 내용")
    String content
) {

}
