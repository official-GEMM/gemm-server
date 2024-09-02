package com.example.gemm_server.dto.generator.response;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "활동 방법 내용 응답", requiredProperties = {"title", "content"})
public record ContentResponse(
    @Schema(description = "활동 방법 제목")
    String title,
    @Schema(description = "활동 방법 내용")
    String content
) {

}
