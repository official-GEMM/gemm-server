package com.example.gemm_server.dto.storage.response;

import com.example.gemm_server.dto.common.Guides;
import com.example.gemm_server.dto.common.PageInformation;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
public class GetGeneratedGuidesResponse {

  @Schema(description = "생성한 활동 방법 리스트")
  private Guides[] guides;

  @Schema(description = "페이지 정보")
  private PageInformation pageInfo;

  public GetGeneratedGuidesResponse() {
  }
}
