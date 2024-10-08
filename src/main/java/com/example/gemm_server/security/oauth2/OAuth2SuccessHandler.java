package com.example.gemm_server.security.oauth2;

import com.example.gemm_server.common.util.CookieUtil;
import com.example.gemm_server.security.jwt.TokenProvider;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

@RequiredArgsConstructor
@Component
public class OAuth2SuccessHandler implements AuthenticationSuccessHandler {

  private final TokenProvider tokenProvider;

  @Value("${authentication.domain.cookie.refresh-token}")
  private String refreshTokenCookieDomain;
  @Value("${authentication.url.login-redirect}")
  private String loginRedirectUrl;

  @Override
  public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
      Authentication authentication) throws IOException {
    String refreshToken = tokenProvider.generateRefreshTokenAndSave(authentication);
    ResponseCookie refreshTokenCookie = CookieUtil.createForRefreshToken(refreshToken,
        refreshTokenCookieDomain);
    response.addHeader(HttpHeaders.SET_COOKIE, refreshTokenCookie.toString());

    CustomOauth2User user = (CustomOauth2User) authentication.getPrincipal();
    String redirectWithParams = UriComponentsBuilder.fromUriString(loginRedirectUrl)
        .queryParam("isJoinCompensationReceived", user.isJoinCompensationReceived())
        .toUriString();
    response.sendRedirect(redirectWithParams);
  }
}