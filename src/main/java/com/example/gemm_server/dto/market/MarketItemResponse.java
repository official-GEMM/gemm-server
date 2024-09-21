package com.example.gemm_server.dto.market;

import com.example.gemm_server.domain.entity.MarketItem;
import com.example.gemm_server.dto.common.response.ActivityResponse;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
@Schema(description = "마켓 상품 응답", requiredProperties = {"marketItemId", "title", "thumbnailPath",
    "age", "category", "materialType", "sellerNickname", "reviewAverageScore", "reviewAverageScore",
    "reviewCount", "price", "isScrapped"})
public class MarketItemResponse extends ActivityResponse {

  @Schema(description = "마켓 상품 아이디")
  private long marketItemId;

  @Schema(description = "판매자 닉네임")
  private String sellerNickname;

  @Schema(description = "리뷰 평점")
  private float reviewAverageScore;

  @Schema(description = "리뷰 수")
  private int reviewCount;

  @Schema(description = "가격")
  private int price;

  @Schema(description = "내 스크랩 여부")
  private Boolean isScrapped;

  public MarketItemResponse(MarketItemBundle marketItemBundle) {
    super(marketItemBundle.getActivity(), marketItemBundle.getThumbnailPath());
    MarketItem marketItem = marketItemBundle.getMarketItem();
    this.marketItemId = marketItem.getId();
    this.reviewAverageScore = marketItem.getAverageScore();
    this.reviewCount = marketItem.getReviewCount();
    this.price = marketItem.getPrice();
    this.sellerNickname = marketItemBundle.getSeller().getNickname();
    this.isScrapped = marketItemBundle.isScrapped();
  }

}