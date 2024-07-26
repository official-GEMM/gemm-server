package com.example.gemm_server.dto.common;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
public class PageInformation {

  @Schema(description = "현재 페이지")
  private int currentPage;

  @Schema(description = "총 페이지 수")
  private int totalPages;

  public PageInformation() {
  }
}
