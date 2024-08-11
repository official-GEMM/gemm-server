package com.example.gemm_server.dto.storage.response;

import com.example.gemm_server.domain.entity.Generation;
import com.example.gemm_server.dto.storage.GeneratedActivityResponse;
import com.example.gemm_server.dto.storage.GeneratedGuideResponse;
import com.example.gemm_server.dto.storage.GenerationWithThumbnail;
import com.example.gemm_server.dto.storage.PurchasedActivityResponse;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import lombok.Getter;

@Getter
@Schema(description = "내 저장소 응답", requiredProperties = {"guides", "activities", "purchases"})
public class GetStorageResponse {

  @Schema(description = "생성한 활동 방법 리스트")
  private GeneratedGuideResponse[] guides;

  @Schema(description = "생성한 활동 리스트")
  private GeneratedActivityResponse[] activities;

  @Schema(description = "구매한 활동 리스트")
  private PurchasedActivityResponse[] purchases;

  public GetStorageResponse(List<Generation> guides,
      List<GenerationWithThumbnail> generationWithThumbnails, List<Object> purchases) {
    this.guides = guides.stream().map(GeneratedGuideResponse::new)
        .toArray(GeneratedGuideResponse[]::new);
    this.activities = generationWithThumbnails.stream().map(GeneratedActivityResponse::new)
        .toArray(GeneratedActivityResponse[]::new);
    this.purchases = purchases.toArray(PurchasedActivityResponse[]::new);
  }
}
