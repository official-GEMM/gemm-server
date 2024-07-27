package com.example.gemm_server.dto.storage.response;

import com.example.gemm_server.dto.common.GuideDetail;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
public class GetGeneratedGuideDetailResponse extends GuideDetail {

  @Schema(description = "생성물 아이디")
  private long generationId;

  public GetGeneratedGuideDetailResponse() {
    super();
  }
}
