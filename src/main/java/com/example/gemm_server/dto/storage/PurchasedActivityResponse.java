package com.example.gemm_server.dto.storage;

import com.example.gemm_server.dto.common.response.ActivityResponse;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
@Schema(description = "구매한 활동 방법 응답", requiredProperties = {"dealId", "title", "thumbnailPath",
    "age", "category", "materialType"})
public class PurchasedActivityResponse extends ActivityResponse {

  @Schema(description = "내용")
  private long dealId;

  public PurchasedActivityResponse(DealBundle dealBundle) {
    super(dealBundle.getDeal().getActivity(), dealBundle.getThumbnailPath());
    this.dealId = dealBundle.getDeal().getId();
  }
}
