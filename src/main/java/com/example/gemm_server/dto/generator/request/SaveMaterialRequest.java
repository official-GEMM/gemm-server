package com.example.gemm_server.dto.generator.request;

import com.example.gemm_server.common.enums.Category;
import com.example.gemm_server.common.util.MaterialUtil;
import com.example.gemm_server.domain.entity.Activity;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record SaveMaterialRequest(
    @Schema(description = "생성하고자 하는 주제")
    @NotBlank
    String title,

    @Schema(description = "교육 대상 연령")
    @NotNull
    Short age,

    @Schema(description = "교육하고자 하는 활동 방식")
    @NotNull
    Category category,

    @Schema(description = "생성 추가 전달 내용")
    String content,

    @Schema(description = "PPT 파일 주소")
    String ppt,

    @Schema(description = "활동지 파일 주소")
    String activitySheet,

    @Schema(description = "컷/도안 파일 주소")
    String cutout
) {

  public Activity toEntity() {
    return Activity.builder()
        .title(title())
        .age(age())
        .category(category())
        .content(content())
        .materialType(MaterialUtil.getMaterialBitMask(ppt(),
            activitySheet(), cutout()))
        .build();
  }
}
