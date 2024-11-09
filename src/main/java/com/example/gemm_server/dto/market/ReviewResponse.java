package com.example.gemm_server.dto.market;

import com.example.gemm_server.domain.entity.Review;
import com.example.gemm_server.dto.common.response.MemberWithProfileImageResponse;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;
import lombok.Getter;

@Getter
@Schema(description = "리뷰 응답", requiredProperties = {"reviewId", "writer", "score", "content",
    "createdAt"})
public class ReviewResponse {

  @Schema(description = "리뷰 아이디")
  private long reviewId;

  @Schema(description = "작성자")
  private MemberWithProfileImageResponse writer;

  @Schema(description = "점수")
  private float score;

  @Schema(description = "내용")
  private String content;

  @Schema(description = "생성 일시")
  private LocalDateTime createdAt;

  public ReviewResponse(ReviewBundle reviewBundle) {
    Review review = reviewBundle.getReview();
    this.reviewId = review.getId();
    this.writer = new MemberWithProfileImageResponse(reviewBundle.getWriter());
    this.score = review.getScore();
    this.content = review.getContent();
    this.createdAt = review.getCreatedAt();
  }
}