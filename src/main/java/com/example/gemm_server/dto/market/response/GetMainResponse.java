package com.example.gemm_server.dto.market.response;

import com.example.gemm_server.dto.market.MarketItemsResponse;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
public class GetMainResponse {

  @Schema(description = "배너 이미지 경로")
  private String[] bannerImagePaths;

  @Schema(description = "추천 상품")
  private MarketItemsResponse[] recommendedMarketItems;

  @Schema(description = "인기 상품 (스크랩 순)")
  private MarketItemsResponse[] mostScrapedMarketItems;

  public GetMainResponse() {
  }
}
