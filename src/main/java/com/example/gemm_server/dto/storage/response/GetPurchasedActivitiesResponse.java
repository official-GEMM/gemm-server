package com.example.gemm_server.dto.storage.response;

import com.example.gemm_server.dto.common.PageInformation;
import com.example.gemm_server.dto.storage.PurchasedActivities;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
public class GetPurchasedActivitiesResponse {

  @Schema(description = "구매한 활동 리스트")
  private PurchasedActivities[] activities;

  @Schema(description = "페이지 정보")
  private PageInformation pageInfo;

  public GetPurchasedActivitiesResponse() {
  }
}
