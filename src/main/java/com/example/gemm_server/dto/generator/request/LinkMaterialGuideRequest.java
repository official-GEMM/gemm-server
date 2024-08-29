package com.example.gemm_server.dto.generator.request;

import com.example.gemm_server.common.enums.Category;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.List;

public record LinkMaterialGuideRequest(
    @Schema(description = "생성하고자 하는 주제")
    @NotBlank
    String title,
    @Schema(description = "교육 대상 연령")
    @NotNull
    Short age,
    @Schema(description = "교육하고자 하는 활동 방식")
    @NotNull
    Category category,
    @Schema(description = "생성 내용")
    @NotNull
    @Size(min = 1)
    List<ContentRequest> contents
) {

}
