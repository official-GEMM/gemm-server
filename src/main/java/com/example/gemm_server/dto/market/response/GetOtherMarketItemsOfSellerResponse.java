package com.example.gemm_server.dto.market.response;

import com.example.gemm_server.dto.market.MarketItemBundle;
import com.example.gemm_server.dto.market.MarketItemResponse;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import lombok.Getter;

@Getter
@Schema(description = "판매자의 다른 상품 리스트 응답", requiredProperties = {"marketItems"})
public class GetOtherMarketItemsOfSellerResponse {

  @Schema(description = "마켓 상품 리스트")
  private MarketItemResponse[] marketItems;

  public GetOtherMarketItemsOfSellerResponse(List<MarketItemBundle> marketItemBundles) {
    this.marketItems = marketItemBundles.stream().map(MarketItemResponse::new)
        .toArray(MarketItemResponse[]::new);
  }
}
