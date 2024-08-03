package com.example.gemm_server.dto.auth.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
public class CheckNicknameDuplicationResponse {

  @Schema(description = "닉네임 중복 여부")
  private Boolean isDuplicated;

  public CheckNicknameDuplicationResponse(boolean isDuplicated) {
    this.isDuplicated = isDuplicated;
  }
}
