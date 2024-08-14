package com.example.gemm_server.dto.generator.response;

public record LlmDesignedMaterialResponse(
    String[] ppt,
    String activitySheet,
    String cutout
) {

}
