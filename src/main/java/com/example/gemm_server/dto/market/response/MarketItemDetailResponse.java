package com.example.gemm_server.dto.market.response;

import com.example.gemm_server.domain.entity.MarketItem;
import com.example.gemm_server.domain.entity.Material;
import com.example.gemm_server.dto.common.response.ActivityDetailResponse;
import com.example.gemm_server.dto.common.response.MemberResponse;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import lombok.Getter;

@Getter
@Schema(description = "마켓 상품 상세 응답", requiredProperties = {"marketItemId", "title", "materials",
    "age", "category", "contents", "scrapCount", "isScrapped", "seller", "isOwnedByCurrentMember",
    "isPurchased", "reviewAverageScore", "reviewCount", "price"})
public class MarketItemDetailResponse extends ActivityDetailResponse {

  @Schema(description = "마켓 상품 아이디")
  private long marketItemId;

  @Schema(description = "스크랩 수")
  private int scrapCount;

  @Schema(description = "내 스크랩 여부")
  private Boolean isScrapped;

  @Schema(description = "판매자")
  private MemberResponse seller;

  @Schema(description = "내 마켓 상품 여부")
  private Boolean isOwnedByCurrentMember;

  @Schema(description = "구매 여부")
  private Boolean isPurchased;

  @Schema(description = "리뷰 평점")
  private float reviewAverageScore;

  @Schema(description = "리뷰 수")
  private int reviewCount;

  @Schema(description = "가격")
  private int price;

  @JsonInclude(JsonInclude.Include.NON_NULL)
  @Schema(description = "연도")
  private Short year;

  @JsonInclude(JsonInclude.Include.NON_NULL)
  @Schema(description = "월")
  private Short month;

  public MarketItemDetailResponse(MarketItem marketItem, List<Material> materialsWithThumbnail,
      boolean isScrapped, boolean isPurchased, Long currentMemberId) {
    super(marketItem.getActivity(), materialsWithThumbnail);
    this.marketItemId = marketItem.getId();
    this.scrapCount = marketItem.getScrapCount();
    this.isScrapped = isScrapped;
    this.seller = new MemberResponse(marketItem.getOwner());
    this.isOwnedByCurrentMember = marketItem.getOwner().getId().equals(currentMemberId);
    this.isPurchased = isPurchased;
    this.reviewAverageScore = marketItem.getAverageScore();
    this.reviewCount = marketItem.getReviewCount();
    this.price = marketItem.getPrice();
    this.year = marketItem.getYear();
    this.month = marketItem.getMonth();
  }
}