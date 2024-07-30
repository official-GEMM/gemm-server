package com.example.gemm_server.dto.market.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
public class MarketItemIdResponse {

  @Schema(description = "생성된 마켓 상품의 아이디")
  private long marketItemId;

  public MarketItemIdResponse() {
  }
}
