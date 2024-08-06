package com.example.gemm_server.dto.common.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
@Schema(description = "젬 응답", requiredProperties = {"gem"})
public class GemResponse {

  @Schema(description = "보유 젬 개수")
  private int gem;

  public GemResponse(int gem) {
    this.gem = gem;
  }
}
