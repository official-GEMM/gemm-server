package com.example.gemm_server.dto.generator.response;

public record LlmMaterialResponse(
    LlmPptResponse ppt,
    LlmActivitySheetResponse activitySheet,
    String cutout
) {

}
