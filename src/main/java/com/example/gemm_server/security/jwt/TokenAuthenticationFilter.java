package com.example.gemm_server.security.jwt;

import io.micrometer.common.lang.NonNull;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@RequiredArgsConstructor
@Component
public class TokenAuthenticationFilter extends OncePerRequestFilter {

  private final TokenProvider tokenProvider;

  @Override
  protected void doFilterInternal(@NonNull HttpServletRequest request,
      @NonNull HttpServletResponse response,
      @NonNull FilterChain filterChain) throws ServletException, IOException {
    String accessToken = tokenProvider.resolveToken(request);

    if (tokenProvider.validateToken(accessToken)) {
      setAuthentication(accessToken);
    }
    filterChain.doFilter(request, response);
  }

  private void setAuthentication(String accessToken) {
    Authentication authentication = tokenProvider.getAuthentication(accessToken);
    SecurityContextHolder.getContext().setAuthentication(authentication);
  }
}