package com.example.gemm_server.dto.common.response;

import com.example.gemm_server.dto.common.PageInfo;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
@Schema(description = "페이지 정보 응답", requiredProperties = {"currentPage", "totalPages"})
public class PageInformationResponse {

  @Schema(description = "현재 페이지")
  private int currentPage;

  @Schema(description = "총 페이지 수")
  private int totalPages;

  public PageInformationResponse(PageInfo pageInfo) {
    this.currentPage = pageInfo.currentPage();
    this.totalPages = pageInfo.totalPage();
  }
}
