package com.example.gemm_server.dto.generator.request;

import com.example.gemm_server.common.enums.Category;
import com.example.gemm_server.dto.common.response.ContentResponse;

public record LlmLinkMaterialGuideRequest(
    String title,
    Short age,
    Category category,
    ContentResponse[] contents
) {

}
