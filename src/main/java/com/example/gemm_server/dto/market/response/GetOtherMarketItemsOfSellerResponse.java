package com.example.gemm_server.dto.market.response;

import com.example.gemm_server.dto.market.MarketItemsResponse;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
public class GetOtherMarketItemsOfSellerResponse {

  @Schema(description = "마켓 아이템 리스트")
  private MarketItemsResponse[] marketItems;

  public GetOtherMarketItemsOfSellerResponse() {
  }
}
