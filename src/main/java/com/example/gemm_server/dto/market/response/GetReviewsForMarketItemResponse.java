package com.example.gemm_server.dto.market.response;

import com.example.gemm_server.dto.common.PageInfo;
import com.example.gemm_server.dto.common.response.PageInformationResponse;
import com.example.gemm_server.dto.market.ReviewBundle;
import com.example.gemm_server.dto.market.ReviewResponse;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
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

  public GetReviewsForMarketItemResponse(List<ReviewBundle> reviewBundles, PageInfo pageInfo,
      Long currentMemberId) {
    Map<Boolean, List<ReviewBundle>> partitionedMap = reviewBundles.stream()
        .collect(Collectors.partitioningBy(review -> review.getWriter().getMember().getId()
            .equals(currentMemberId)));

    Optional<ReviewBundle> myReviewBundle = partitionedMap.get(true).stream().findFirst();
    this.myReview = myReviewBundle.map(ReviewResponse::new).orElse(null);
    this.otherReviews = partitionedMap.get(false).stream().map(ReviewResponse::new)
        .toArray(ReviewResponse[]::new);
    this.pageInfo = new PageInformationResponse(pageInfo);
  }
}
