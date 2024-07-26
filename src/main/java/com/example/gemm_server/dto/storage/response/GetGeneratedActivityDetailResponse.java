package com.example.gemm_server.dto.storage.response;

import com.example.gemm_server.dto.common.ActivityDetail;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
public class GetGeneratedActivityDetailResponse extends ActivityDetail {

  @Schema(description = "생성물 아이디")
  private long generationId;

  @Schema(description = "마켓 업로드 여부")
  private boolean isMarketUploaded;

  public GetGeneratedActivityDetailResponse() {
    super();
  }
}
