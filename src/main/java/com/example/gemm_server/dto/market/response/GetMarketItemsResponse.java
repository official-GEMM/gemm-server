package com.example.gemm_server.dto.market.response;

import com.example.gemm_server.dto.common.response.PageInformationResponse;
import com.example.gemm_server.dto.market.MarketItemsResponse;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
public class GetMarketItemsResponse {

  @Schema(description = "마켓 아이템 리스트")
  private MarketItemsResponse[] marketItems;

  @Schema(description = "페이지 정보")
  private PageInformationResponse pageInfo;

  public GetMarketItemsResponse() {
  }
}
