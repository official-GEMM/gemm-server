package com.example.gemm_server.dto.generator.response;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "llm 활동 자료 응답", requiredProperties = {"ppt", "activitySheet", "cutout"})
public record LlmMaterialResponse(
    String ppt,
    String activitySheet,
    String cutout
) {

}
