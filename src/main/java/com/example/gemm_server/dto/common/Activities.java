package com.example.gemm_server.dto.common;

import com.example.gemm_server.common.enums.Category;
import com.example.gemm_server.common.enums.MaterialType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import org.springframework.core.io.UrlResource;

@Getter
public class Activities {

  @Schema(description = "제목")
  private String title;

  @Schema(description = "썸네일 경로")
  private UrlResource thumbnailPath;

  @Schema(description = "타겟 연령")
  private int age;

  @Schema(description = "영역 및 활동")
  private Category category;

  @Schema(description = "보유한 자료 종류")
  private MaterialType[] materialType;

  public Activities() {
  }
}
