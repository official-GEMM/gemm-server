package com.example.gemm_server.dto.my.response;

import io.swagger.v3.oas.annotations.media.Schema;

public class UpdateNicknameResponse {

  @Schema(description = "변경된 닉네임")
  private String nickname;
}
