package com.example.gemm_server.dto.storage;

import com.example.gemm_server.dto.common.response.ActivitiesResponse;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
public class PurchasedActivitiesResponse extends ActivitiesResponse {

  @Schema(description = "내용")
  private long dealId;

  public PurchasedActivitiesResponse() {
    super();
  }
}
