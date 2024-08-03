package com.example.gemm_server.dto.my.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
public class UpdateMyNicknameResponse {

  @Schema(description = "변경된 닉네임")
  private String nickname;

  public UpdateMyNicknameResponse(String nickname) {
    this.nickname = nickname;
  }
}
