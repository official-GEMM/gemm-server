package com.example.gemm_server.dto.storage.response;

import com.example.gemm_server.common.enums.Category;
import com.example.gemm_server.domain.entity.Activity;
import com.example.gemm_server.dto.common.response.ActivityDetailResponse;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.ArrayList;
import lombok.Getter;

@Getter
@Schema(description = "구매한 활동 상세 응답", requiredProperties = {"dealId", "title", "materials", "age",
    "category", "content"})
public class GetPurchasedActivityDetailResponse extends ActivityDetailResponse {

  @Schema(description = "생성물 아이디")
  private long dealId;

  public GetPurchasedActivityDetailResponse() {
    super(new Activity("", (short) 0, (short) 0, Category.ART_AREA, ""), new ArrayList<>());
  }
}
