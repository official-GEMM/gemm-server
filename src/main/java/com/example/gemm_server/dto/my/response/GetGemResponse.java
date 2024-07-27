package com.example.gemm_server.dto.my.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
public class GetGemResponse {

  @Schema(description = "보유 젬")
  private int gem;

  public GetGemResponse() {
  }
}
