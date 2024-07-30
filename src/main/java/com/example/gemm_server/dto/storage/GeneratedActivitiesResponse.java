package com.example.gemm_server.dto.storage;

import com.example.gemm_server.dto.common.response.ActivitiesResponse;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
public class GeneratedActivitiesResponse extends ActivitiesResponse {

  @Schema(description = "생성물 아이디")
  private long generationId;

  public GeneratedActivitiesResponse() {
    super();
  }
}
