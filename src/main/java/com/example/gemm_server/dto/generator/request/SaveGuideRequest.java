package com.example.gemm_server.dto.generator.request;

import com.example.gemm_server.common.enums.Category;
import com.example.gemm_server.domain.entity.Activity;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record SaveGuideRequest(
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
    @NotBlank
    String content
) {

  public Activity toEntity() {
    return Activity.builder()
        .title(title())
        .age(age())
        .category(category())
        .content(content())
        .materialType((short) 0)
        .build();
  }
}
