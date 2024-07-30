package com.example.gemm_server.dto.storage.response;

import com.example.gemm_server.dto.common.response.PageInformationResponse;
import com.example.gemm_server.dto.storage.GeneratedActivitiesResponse;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
public class GetGeneratedActivitiesResponse {

  @Schema(description = "생성한 활동 리스트")
  private GeneratedActivitiesResponse[] activities;

  @Schema(description = "페이지 정보")
  private PageInformationResponse pageInfo;

  public GetGeneratedActivitiesResponse() {
  }
}
