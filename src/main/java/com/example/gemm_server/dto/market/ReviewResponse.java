package com.example.gemm_server.dto.market;

import com.example.gemm_server.dto.common.response.MemberWithProfileImageResponse;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;
import lombok.Getter;

@Getter
public class ReviewResponse {

  @Schema(description = "마켓 상품 아이디")
  private long reviewId;

  @Schema(description = "작성자")
  private MemberWithProfileImageResponse writer;

  @Schema(description = "스크랩 수")
  private float score;

  @Schema(description = "내 스크랩 여부")
  private String content;

  @Schema(description = "스크랩 수")
  private LocalDateTime createdAt;

  public ReviewResponse() {
  }
}