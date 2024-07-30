package com.example.gemm_server.dto.market.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PostReviewRequest {

  @NotNull
  @Min(0)
  @Max(5)
  @Schema(description = "점수")
  private Float score;

  @NotBlank
  @Size(max = 200)
  @Schema(description = "리뷰 내용")
  private String content;

}
