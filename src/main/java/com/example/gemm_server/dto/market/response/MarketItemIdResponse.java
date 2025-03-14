package com.example.gemm_server.dto.market.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
@Schema(description = "마켓 상품 아이디 응답", requiredProperties = {"marketItemId"})
public class MarketItemIdResponse {

  @Schema(description = "생성된 마켓 상품의 아이디")
  private long marketItemId;

  public MarketItemIdResponse(Long marketItemId) {
    this.marketItemId = marketItemId;
  }
}
