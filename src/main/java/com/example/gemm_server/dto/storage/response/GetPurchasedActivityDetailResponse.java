package com.example.gemm_server.dto.storage.response;

import com.example.gemm_server.domain.entity.Deal;
import com.example.gemm_server.domain.entity.Material;
import com.example.gemm_server.dto.common.response.ActivityDetailResponse;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import lombok.Getter;

@Getter
@Schema(description = "구매한 활동 상세 응답", requiredProperties = {"dealId", "title", "materials", "age",
    "category", "contents"})
public class GetPurchasedActivityDetailResponse extends ActivityDetailResponse {

  @Schema(description = "생성물 아이디")
  private long dealId;

  public GetPurchasedActivityDetailResponse(Deal deal, List<Material> materials) {
    super(deal.getActivity(), materials);
    this.dealId = deal.getId();
  }
}
