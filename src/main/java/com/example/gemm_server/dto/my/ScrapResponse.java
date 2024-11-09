package com.example.gemm_server.dto.my;

import com.example.gemm_server.common.enums.Category;
import com.example.gemm_server.common.enums.MaterialType;
import com.example.gemm_server.common.util.MaterialUtil;
import com.example.gemm_server.domain.entity.MarketItem;
import com.example.gemm_server.dto.common.response.MemberResponse;
import com.example.gemm_server.dto.market.MarketItemBundle;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
@Schema(description = "스크랩 응답", requiredProperties = {"scrapId", "title", "thumbnailPath",
    "seller", "reviewAverageScore", "reviewCount", "price", "age", "category", "materialType"})
public class ScrapResponse {

  @Schema(description = "스크랩 아이디")
  private long scrapId;

  @Schema(description = "제목")
  private String title;

  @Schema(description = "썸네일 경로")
  private String thumbnailPath;

  @Schema(description = "판매자")
  private MemberResponse seller;

  @Schema(description = "리뷰 평점")
  private float reviewAverageScore;

  @Schema(description = "리뷰 수")
  private Integer reviewCount;

  @Schema(description = "가격")
  private Integer price;

  @Schema(description = "타켓 연령")
  private Short age;

  @Schema(description = "영역 및 활동")
  private Category category;

  @Schema(description = "자료 종류")
  private MaterialType[] materialType;

  public ScrapResponse(ScrapBundle scrapBundle) {
    MarketItemBundle marketItemBundle = scrapBundle.getMarketItemBundle();
    MarketItem marketItem = marketItemBundle.getMarketItem();
    this.scrapId = scrapBundle.getScrap().getId();
    this.title = marketItem.getActivity().getTitle();
    this.thumbnailPath = marketItemBundle.getThumbnailPath();
    this.seller = new MemberResponse(marketItemBundle.getSeller());
    this.reviewAverageScore = marketItem.getAverageScore();
    this.reviewCount = marketItem.getReviewCount();
    this.price = marketItem.getPrice();
    this.age = marketItem.getActivity().getAge();
    this.category = marketItem.getActivity().getCategory();
    this.materialType = MaterialUtil.binaryToList(marketItem.getActivity().getMaterialType())
        .toArray(MaterialType[]::new);
  }
}