package com.example.gemm_server.dto.common;

import com.example.gemm_server.common.enums.Category;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
public class GuideDetail extends Guides {

  @Schema(description = "타켓 연령")
  private int age;

  @Schema(description = "영역 및 활동")
  private Category category;

  public GuideDetail() {
  }
}
