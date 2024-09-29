package com.example.gemm_server.common.util;

import java.time.Duration;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Component;

@Component
public class CookieUtil {

  public static ResponseCookie createForRefreshToken(String refreshToken) {
    return ResponseCookie.from("refreshToken", refreshToken)
        .httpOnly(true)
        .secure(true)
        .path("/")
        .maxAge(Duration.ofDays(7))
        .sameSite("None")
        .build();
  }

  public static ResponseCookie createForRefreshToken(String refreshToken, String domain) {
    return ResponseCookie.from("refreshToken", refreshToken)
        .httpOnly(true)
        .secure(true)
        .path("/")
        .domain(domain)
        .maxAge(Duration.ofDays(7))
        .sameSite("None")
        .build();
  }
}
