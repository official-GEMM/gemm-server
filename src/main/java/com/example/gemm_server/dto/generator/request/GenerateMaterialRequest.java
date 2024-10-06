package com.example.gemm_server.dto.generator.request;

import com.example.gemm_server.common.constant.Policy;
import com.example.gemm_server.common.enums.Category;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import jakarta.validation.constraints.Size;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Stream;

public record GenerateMaterialRequest(
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

    @Schema(description = "PPT 슬라이드에 반영하고 싶은 내용")
    @Size(min = 1, max = 20)
    List<String> ppt,

    @Schema(description = "활동지에 반영하고 싶은 내용")
    String activitySheet,

    @Schema(description = "컷/도안에 반영하고 싶은 내용")
    String cutout
) {

  public boolean isEmptyMaterialRequest() {
    return Stream.of(ppt(), activitySheet(), cutout()).allMatch(Objects::isNull);
  }

  public int getTotalUseGemAmount() {
    return Stream.of(Optional.ofNullable(ppt()).map(p -> Policy.GENERATE_PPT),
        Optional.ofNullable(activitySheet()).map(a -> Policy.GENERATE_ACTIVITY_SHEET),
        Optional.ofNullable(cutout()).map(c -> Policy.GENERATE_CUTOUT)
    ).filter(Optional::isPresent).mapToInt(Optional::get).sum();
  }
}
