package com.example.gemm_server.dto.common;

import com.example.gemm_server.common.enums.Category;
import com.example.gemm_server.common.enums.MaterialType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import org.springframework.core.io.UrlResource;

@Getter
public class ActivityDetail {

  @Schema(description = "제목")
  private String title;

  @Schema(description = "자료 리스트")
  private Material materials;

  @Schema(description = "타겟 연령")
  private int age;

  @Schema(description = "영역 및 활동")
  private Category category;

  @Schema(description = "내용")
  private String content;

  @Getter
  private static class Material {

    @Schema(description = "자료 아이디")
    private long materialId;

    @Schema(description = "자료 종류")
    private MaterialType type;

    @Schema(description = "썸네일 경로 리스트")
    private UrlResource[] thumbnailPaths;
  }
}
