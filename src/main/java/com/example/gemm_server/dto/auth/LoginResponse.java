package com.example.gemm_server.dto.auth;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class LoginResponse {

  private String accessToken;
  private String refreshToken;
  private Boolean isCompleted;
}
