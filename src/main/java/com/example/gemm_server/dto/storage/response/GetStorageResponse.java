package com.example.gemm_server.dto.storage.response;

import com.example.gemm_server.dto.storage.GeneratedActivities;
import com.example.gemm_server.dto.storage.GeneratedGuides;
import com.example.gemm_server.dto.storage.PurchasedActivities;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
public class GetStorageResponse {

  @Schema(description = "생성한 활동 방법 리스트")
  private GeneratedGuides[] guides;

  @Schema(description = "생성한 활동 리스트")
  private GeneratedActivities[] activities;

  @Schema(description = "구매한 활동 리스트")
  private PurchasedActivities[] purchases;

  public GetStorageResponse() {
  }
}
