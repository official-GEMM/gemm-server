package com.example.gemm_server.dto.market.response;

import com.example.gemm_server.common.enums.BannerLocation;
import com.example.gemm_server.common.util.S3Util;
import com.example.gemm_server.domain.entity.Banner;
import com.example.gemm_server.dto.market.MarketItemBundle;
import com.example.gemm_server.dto.market.MarketItemResponse;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import lombok.Getter;

@Getter
@Schema(description = "메인 페이지 정보 응답", requiredProperties = {"bannerImagePaths",
    "recommendedMarketItems", "mostScrapedMarketItems"})
public class GetMainResponse {

  @Schema(description = "배너 이미지 경로")
  private BannerImageResponse[] bannerImages;

  @Schema(description = "추천 상품")
  private MarketItemResponse[] recommendedMarketItems;

  @Schema(description = "인기 상품 (스크랩 순)")
  private MarketItemResponse[] mostScrapedMarketItems;

  @Getter
  @Schema(description = "배너 정보", requiredProperties = {"path", "location"})
  private static class BannerImageResponse {

    @Schema(description = "배너 이미지 경로")
    private String path;

    @Schema(description = "배너 위치")
    private BannerLocation location;

    public BannerImageResponse(Banner banner) {
      this.path = S3Util.getFileUrl(banner.getDirectoryPath() + banner.getFileName());
      this.location = banner.getLocation();
    }
  }

  public GetMainResponse(List<Banner> banners,
      List<MarketItemBundle> recommendedMarketItems,
      List<MarketItemBundle> mostScrapedMarketItems) {
    this.bannerImages = banners.stream().map(BannerImageResponse::new)
        .toArray(BannerImageResponse[]::new);
    this.recommendedMarketItems = recommendedMarketItems.stream().map(MarketItemResponse::new)
        .toArray(MarketItemResponse[]::new);
    this.mostScrapedMarketItems = mostScrapedMarketItems.stream().map(MarketItemResponse::new)
        .toArray(MarketItemResponse[]::new);
  }
}
