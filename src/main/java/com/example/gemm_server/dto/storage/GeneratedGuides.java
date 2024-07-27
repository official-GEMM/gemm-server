package com.example.gemm_server.dto.storage;

import com.example.gemm_server.dto.common.Guides;
import io.swagger.v3.oas.annotations.media.Schema;

public class GeneratedGuides extends Guides {

  @Schema(description = "생성물 아이디")
  private long generationId;

  public GeneratedGuides() {
    super();
  }
}
