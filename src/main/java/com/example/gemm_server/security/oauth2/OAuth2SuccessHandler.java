package com.example.gemm_server.security.oauth2;

import com.example.gemm_server.common.util.CookieUtil;
import com.example.gemm_server.security.jwt.TokenProvider;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseCookie;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

@RequiredArgsConstructor
@Component
public class OAuth2SuccessHandler implements AuthenticationSuccessHandler {

  private final TokenProvider tokenProvider;
  private final CookieUtil cookieUtil;

  private static final String URI = "/auth/login";

  @Override
  public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
      Authentication authentication) throws IOException, ServletException {
    String refreshToken = tokenProvider.generateRefreshTokenAndSave(authentication);
    ResponseCookie refreshTokenCookie = cookieUtil.createForRefreshToken(refreshToken);

    response.addHeader("Set-Cookie", refreshTokenCookie.toString());
    String redirectUrl = UriComponentsBuilder.fromUriString(URI).toUriString();
    response.sendRedirect(redirectUrl);
  }
}