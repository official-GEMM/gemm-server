package com.example.gemm_server.dto.storage.response;

import com.example.gemm_server.dto.common.response.ActivityDetailResponse;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
@Schema(description = "구매한 활동 상세 응답", requiredProperties = {"dealId", "title", "materials", "age",
    "category", "content"})
public class GetPurchasedActivityDetailResponse extends ActivityDetailResponse {

  @Schema(description = "생성물 아이디")
  private long dealId;

  public GetPurchasedActivityDetailResponse() {
    super();
  }
}
