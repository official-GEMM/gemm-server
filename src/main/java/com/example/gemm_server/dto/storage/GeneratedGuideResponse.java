package com.example.gemm_server.dto.storage;

import com.example.gemm_server.dto.common.response.GuideResponse;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
@Schema(description = "생성한 활동 방법 응답", requiredProperties = {"generationId", "title", "content"})
public class GeneratedGuideResponse extends GuideResponse {

  @Schema(description = "생성물 아이디")
  private long generationId;

  public GeneratedGuideResponse() {
    super();
  }
}
