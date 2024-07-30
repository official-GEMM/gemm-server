package com.example.gemm_server.dto.storage.response;

import com.example.gemm_server.dto.storage.GeneratedActivitiesResponse;
import com.example.gemm_server.dto.storage.GeneratedGuidesResponse;
import com.example.gemm_server.dto.storage.PurchasedActivitiesResponse;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
public class GetStorageResponse {

  @Schema(description = "생성한 활동 방법 리스트")
  private GeneratedGuidesResponse[] guides;

  @Schema(description = "생성한 활동 리스트")
  private GeneratedActivitiesResponse[] activities;

  @Schema(description = "구매한 활동 리스트")
  private PurchasedActivitiesResponse[] purchases;

  public GetStorageResponse() {
  }
}
