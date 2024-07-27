package com.example.gemm_server.dto.storage.response;

import com.example.gemm_server.dto.common.ActivityDetail;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
public class GetPurchasedActivityDetailResponse extends ActivityDetail {

  @Schema(description = "생성물 아이디")
  private long dealId;

  public GetPurchasedActivityDetailResponse() {
    super();
  }
}
