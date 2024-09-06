package com.example.gemm_server.dto.generator.response;

import com.example.gemm_server.dto.common.response.ContentResponse;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "생성한 활동 방법 응답", requiredProperties = {"contents", "gem"})
public record GenerateGuideResponse(
    @Schema(description = "내용과 형식")
    ContentResponse[] contents,

    @Schema(description = "잔여 젬")
    int gem
) {

}
