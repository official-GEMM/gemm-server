package com.example.gemm_server.dto.generator.response;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;

@Schema(description = "llm 활동 방법 응답", requiredProperties = {"contents"})
public record LlmGuideResponse(
    List<ContentResponse> contents
) {

}
