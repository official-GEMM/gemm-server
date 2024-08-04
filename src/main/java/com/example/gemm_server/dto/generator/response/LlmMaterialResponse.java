package com.example.gemm_server.dto.generator.response;

public record LlmMaterialResponse(
        String ppt,
        String activitySheet,
        String cutout
) {
}
