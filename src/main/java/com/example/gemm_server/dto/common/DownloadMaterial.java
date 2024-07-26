package com.example.gemm_server.dto.common;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import org.springframework.core.io.UrlResource;

@Getter
public class DownloadMaterial {

  @Schema(description = "자료 다운로드 경로 리스트")
  private UrlResource[] materialPaths;

  public DownloadMaterial() {
  }
}
