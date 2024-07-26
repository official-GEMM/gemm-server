package com.example.gemm_server.dto.auth.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class LoginResponse {

  @Schema(description = "access token")
  private String accessToken;

  @Schema(description = "refresh token")
  private String refreshToken;

  @Schema(description = "모든 필수 정보 작성 여부")
  private Boolean isCompleted;
}
