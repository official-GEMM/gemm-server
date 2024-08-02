package com.example.gemm_server.dto.common.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
public class DownloadMaterialResponse {

  @Schema(description = "자료 다운로드 경로 리스트")
  private String[] materialPaths;

  public DownloadMaterialResponse() {
  }
}
