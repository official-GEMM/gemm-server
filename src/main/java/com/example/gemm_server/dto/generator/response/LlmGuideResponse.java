package com.example.gemm_server.dto.generator.response;

import com.example.gemm_server.dto.common.response.ContentResponse;

public record LlmGuideResponse(
    ContentResponse[] contents
) {

}
