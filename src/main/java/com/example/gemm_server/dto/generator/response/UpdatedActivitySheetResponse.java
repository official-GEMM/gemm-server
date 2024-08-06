package com.example.gemm_server.dto.generator.response;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "수정된 활동지 응답", requiredProperties = {"activitySheet", "gem"})
public record UpdatedActivitySheetResponse(
    @Schema(description = "수정된 활동지 정보")
    CommentedActivitySheetResponse activitySheet,
    @Schema(description = "잔여 젬")
    int gem
) {

}
