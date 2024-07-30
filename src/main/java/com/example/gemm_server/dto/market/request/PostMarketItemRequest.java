package com.example.gemm_server.dto.market.request;

import com.example.gemm_server.dto.common.request.ActivityDetailRequest;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PostMarketItemRequest extends ActivityDetailRequest {

  @Schema(description = "연도")
  private int year;

  @Min(1)
  @Max(12)
  @Schema(description = "월")
  private short month;

}