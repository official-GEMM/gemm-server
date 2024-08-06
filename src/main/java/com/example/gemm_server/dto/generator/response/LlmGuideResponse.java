package com.example.gemm_server.dto.generator.response;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "llm 활동 방법 응답", requiredProperties = {"content"})
public record LlmGuideResponse(
    String content
) {

}
