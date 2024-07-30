package com.example.gemm_server.dto.common.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
public class MemberResponse {

  @Schema(description = "사용자 아이디")
  private long memberId;

  @Schema(description = "사용자 닉네임")
  private String nickname;

  public MemberResponse() {
  }
}
