package com.example.gemm_server.dto.storage.response;

import com.example.gemm_server.dto.common.response.ActivityDetailResponse;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
@Schema(description = "생성한 활동 상세 응답", requiredProperties = {"generationId", "title", "materials",
    "age", "category", "content", "isMarketUploaded"})
public class GetGeneratedActivityDetailResponse extends ActivityDetailResponse {

  @Schema(description = "생성물 아이디")
  private long generationId;

  @Schema(description = "마켓 업로드 여부")
  private Boolean isMarketUploaded;

  public GetGeneratedActivityDetailResponse() {
    super();
  }
}
