package com.example.gemm_server.dto.common.request;

import com.example.gemm_server.common.enums.Category;
import com.example.gemm_server.common.enums.MaterialType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FilterRequest {

  @Schema(description = "연령")
  private Short age;

  @Schema(description = "영역 및 활동")
  private Category category;

  @Schema(description = "연도")
  private Integer year;

  @Min(1)
  @Max(12)
  @Schema(description = "월")
  private Short month;

  @Schema(description = "자료 종류")
  private MaterialType[] materialType;

  @Schema(description = "무료 여부")
  private Boolean isFree;
}