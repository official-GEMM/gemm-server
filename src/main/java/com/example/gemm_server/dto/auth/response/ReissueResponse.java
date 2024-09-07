package com.example.gemm_server.dto.auth.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
@Schema(description = "리이슈 응답", requiredProperties = {"accessToken"})
public class ReissueResponse {

  @Schema(description = "access token")
  private String accessToken;

  public ReissueResponse(String accessToken) {
    this.accessToken = accessToken;
  }
}