package com.example.gemm_server.dto.generator.response;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "수정된 활동 방법 응답", requiredProperties = {"content", "gem"})
public record UpdatedGuideResponse(
    @Schema(description = "수정하여 생성한 내용")
    String content,
    @Schema(description = "잔여 젬")
    int gem
) {

}
