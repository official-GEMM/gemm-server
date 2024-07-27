package com.example.gemm_server.dto.storage;

import com.example.gemm_server.dto.common.Activities;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
public class PurchasedActivities extends Activities {

  @Schema(description = "내용")
  private long dealId;

  public PurchasedActivities() {
    super();
  }
}
