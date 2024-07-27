package com.example.gemm_server.dto.common;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
public class Guides {

  @Schema(description = "제목")
  private String title;

  @Schema(description = "내용")
  private String content;

  public Guides() {
  }
}
