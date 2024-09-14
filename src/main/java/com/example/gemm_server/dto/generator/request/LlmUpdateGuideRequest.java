package com.example.gemm_server.dto.generator.request;

import com.example.gemm_server.dto.common.response.ContentResponse;
import java.util.List;

public record LlmUpdateGuideRequest(
    List<ContentResponse> contents,
    List<CommentGuideRequest> comments
) {

}
