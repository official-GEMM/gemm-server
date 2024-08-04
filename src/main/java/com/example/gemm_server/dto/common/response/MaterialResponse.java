package com.example.gemm_server.dto.common.response;

import com.example.gemm_server.common.enums.MaterialType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
public class MaterialResponse {

  @Schema(description = "자료 아이디")
  private long materialId;

  @Schema(description = "자료 종류")
  private MaterialType type;

  @Schema(description = "썸네일 경로 리스트")
  private String[] thumbnailPaths;

  public MaterialResponse() {
  }
}