package com.example.gemm_server.dto.market.response;

import com.example.gemm_server.common.enums.Category;
import com.example.gemm_server.domain.entity.Activity;
import com.example.gemm_server.dto.common.response.ActivityDetailResponse;
import com.example.gemm_server.dto.common.response.MemberResponse;
import com.fasterxml.jackson.annotation.JsonInclude;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.ArrayList;
import lombok.Getter;

@Getter
@Schema(description = "마켓 상품 상세 응답", requiredProperties = {"marketItemId", "title", "materials",
    "age", "category", "contents", "scrapCount", "isScrapped", "seller", "isMyMarketItem",
    "isBought", "reviewAverageScore", "reviewCount", "price"})
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
  private Boolean isMyMarketItem;

  @Schema(description = "구매 여부")
  private Boolean isBought;

  @Schema(description = "리뷰 평점")
  private float reviewAverageScore;

  @Schema(description = "리뷰 수")
  private int reviewCount;

  @Schema(description = "가격")
  private int price;

  @JsonInclude(JsonInclude.Include.NON_NULL)
  @Schema(description = "연도")
  private int year;

  @JsonInclude(JsonInclude.Include.NON_NULL)
  @Schema(description = "월")
  private short month;

  public MarketItemDetailResponse() {
    super(new Activity("", (short) 0, (short) 0, Category.ART_AREA, ""), new ArrayList<>());
  }
}