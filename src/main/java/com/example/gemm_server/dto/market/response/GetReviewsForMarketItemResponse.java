package com.example.gemm_server.dto.market.response;

import com.example.gemm_server.dto.common.response.PageInformationResponse;
import com.example.gemm_server.dto.market.ReviewResponse;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
@Schema(description = "상품에 대한 리뷰 응답", requiredProperties = {"otherReviews", "pageInfo"})
public class GetReviewsForMarketItemResponse {

  @JsonInclude(JsonInclude.Include.NON_NULL)
  @Schema(description = "내 리뷰")
  private ReviewResponse myReview;

  @Schema(description = "다른 사용자의 리뷰 리스트")
  private ReviewResponse[] otherReviews;

  @Schema(description = "페이지 정보")
  private PageInformationResponse pageInfo;

  public GetReviewsForMarketItemResponse() {
  }
}
