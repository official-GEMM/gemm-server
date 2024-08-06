package com.example.gemm_server.dto.storage;

import com.example.gemm_server.dto.common.response.ActivityResponse;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
@Schema(description = "생성한 활동 응답", requiredProperties = {"generationId", "title", "thumbnailPath",
    "age", "category", "materialType"})
public class GeneratedActivityResponse extends ActivityResponse {

  @Schema(description = "생성물 아이디")
  private long generationId;

  public GeneratedActivityResponse() {
    super();
  }
}
