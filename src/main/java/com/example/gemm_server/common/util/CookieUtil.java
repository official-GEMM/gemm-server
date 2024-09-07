package com.example.gemm_server.common.util;

import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Component;

@Component
public class CookieUtil {

  private static final int refreshTokenCookieExpiration = 7 * 24 * 60 * 60;  // 7일

  public static ResponseCookie createForRefreshToken(String refreshToken) {
    return ResponseCookie.from("refreshToken", refreshToken)
        .httpOnly(true)
        .secure(true)
        .path("/")
        .maxAge(refreshTokenCookieExpiration)
        .sameSite("None")
        .build();
  }
}
