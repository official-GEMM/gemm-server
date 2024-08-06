package com.example.gemm_server.dto.generator.response;

import java.util.List;

public record LlmDesignedMaterialResponse(
    String[] ppt,
    String activitySheet,
    String cutout
) {

}
