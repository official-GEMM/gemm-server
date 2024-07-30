package com.example.gemm_server.dto.storage.response;

import com.example.gemm_server.dto.common.response.GuideDetailResponse;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
public class GetGeneratedGuideDetailResponse extends GuideDetailResponse {

  @Schema(description = "생성물 아이디")
  private long generationId;

  public GetGeneratedGuideDetailResponse() {
    super();
  }
}
