package com.example.gemm_server.dto.storage.response;

import com.example.gemm_server.dto.common.response.PageInformationResponse;
import com.example.gemm_server.dto.storage.PurchasedActivityResponse;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
@Schema(description = "구매한 활동 리스트 응답", requiredProperties = {"activities", "pageInfo"})
public class GetPurchasedActivitiesResponse {

  @Schema(description = "구매한 활동 리스트")
  private PurchasedActivityResponse[] activities;

  @Schema(description = "페이지 정보")
  private PageInformationResponse pageInfo;

  public GetPurchasedActivitiesResponse() {
  }
}
