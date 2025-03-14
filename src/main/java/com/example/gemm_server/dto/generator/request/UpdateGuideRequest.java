package com.example.gemm_server.dto.generator.request;

import com.example.gemm_server.dto.common.response.ContentResponse;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.List;

public record UpdateGuideRequest(
    @Schema(description = "수정할 내용")
    @NotNull
    @Size(min = 1)
    List<ContentResponse> contents,

    @Schema(description = "수정에 적용할 코멘트")
    @NotNull
    @Size(min = 1)
    List<CommentGuideRequest> comments
) {

}
