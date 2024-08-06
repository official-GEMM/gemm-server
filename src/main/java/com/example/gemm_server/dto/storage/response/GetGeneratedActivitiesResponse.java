package com.example.gemm_server.dto.storage.response;

import com.example.gemm_server.dto.common.response.PageInformationResponse;
import com.example.gemm_server.dto.storage.GeneratedActivityResponse;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
@Schema(description = "생성한 활동 리스트 응답", requiredProperties = {"activities", "pageInfo"})
public class GetGeneratedActivitiesResponse {

  @Schema(description = "생성한 활동 리스트")
  private GeneratedActivityResponse[] activities;

  @Schema(description = "페이지 정보")
  private PageInformationResponse pageInfo;

  public GetGeneratedActivitiesResponse() {
  }
}
