package com.example.gemm_server.dto.auth;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Token {

  @Schema(description = "access token")
  private String accessToken;

  @Schema(description = "refresh token")
  private String refreshToken;
}
