package com.example.gemm_server.dto.my.response;

import com.example.gemm_server.dto.common.PageInformation;
import com.example.gemm_server.dto.my.Scraps;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
public class GetMyScrapsResponse {

  @Schema(description = "내 스크랩 리스트")
  private Scraps[] scraps;

  @Schema(description = "페이지 정보")
  private PageInformation pageInfo;

  public GetMyScrapsResponse() {
  }
}
