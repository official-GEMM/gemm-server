package com.example.gemm_server.dto.storage.response;

import com.example.gemm_server.dto.common.response.GuideResponse;
import com.example.gemm_server.dto.common.response.PageInformationResponse;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
@Schema(description = "생성한 활동 방법 리스트 응답", requiredProperties = {"guides", "pageInfo"})
public class GetGeneratedGuidesResponse {

  @Schema(description = "생성한 활동 방법 리스트")
  private GuideResponse[] guides;

  @Schema(description = "페이지 정보")
  private PageInformationResponse pageInfo;

  public GetGeneratedGuidesResponse() {
  }
}
