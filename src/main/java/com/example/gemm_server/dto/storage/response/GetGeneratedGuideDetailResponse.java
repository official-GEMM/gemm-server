package com.example.gemm_server.dto.storage.response;

import com.example.gemm_server.domain.entity.Generation;
import com.example.gemm_server.dto.common.response.GuideDetailResponse;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
@Schema(description = "생성한 활동 방법 상세 응답", requiredProperties = {"generationId", "age", "category"})
public class GetGeneratedGuideDetailResponse extends GuideDetailResponse {

  @Schema(description = "생성물 아이디")
  private long generationId;

  public GetGeneratedGuideDetailResponse(Generation generation) {
    super(generation.getActivity());
    this.generationId = generation.getId();
  }
}
