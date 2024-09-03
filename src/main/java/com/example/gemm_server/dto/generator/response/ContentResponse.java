package com.example.gemm_server.dto.generator.response;

import com.example.gemm_server.common.enums.Format;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "활동 방법 내용 응답", requiredProperties = {"format", "content"})
public record ContentResponse(
    @Schema(description = "활동 방법 내용 형식")
    Format format,

    @Schema(description = "활동 방법 내용")
    String content
) {

}
