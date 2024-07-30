package com.example.gemm_server.dto.market.response;

import com.example.gemm_server.dto.common.response.PageInformationResponse;
import com.example.gemm_server.dto.market.ReviewResponse;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
public class GetReviewsForMarketItemResponse {

  @Schema(description = "내 리뷰")
  private ReviewResponse myReview;

  @Schema(description = "다른 사용자의 리뷰 리스트")
  private ReviewResponse[] otherReviews;

  @Schema(description = "페이지 정보")
  private PageInformationResponse pageInfo;

  public GetReviewsForMarketItemResponse() {
  }
}
