package com.example.gemm_server.common.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Component;

@Component
public class CookieUtil {

  private static final int refreshTokenCookieExpiration = 7 * 24 * 60 * 60;  // 7일

  @Value("${login.cookie.secure}")
  private boolean secure;

  public ResponseCookie createForRefreshToken(String refreshToken) {
    return ResponseCookie.from("refreshToken", refreshToken)
        .httpOnly(true)
        .secure(secure)
        .path("/")
        .maxAge(refreshTokenCookieExpiration)
        .sameSite("None")
        .build();
  }

}
