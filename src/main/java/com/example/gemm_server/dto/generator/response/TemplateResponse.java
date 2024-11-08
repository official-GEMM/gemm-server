package com.example.gemm_server.dto.generator.response;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "템플릿 응답", requiredProperties = {"templateNumber", "thumbnailPath"})
public record TemplateResponse(
    @Schema(description = "템플릿 번호")
    short templateNumber,

    @Schema(description = "대표 썸네일 주소")
    String thumbnailPath
) {
}
