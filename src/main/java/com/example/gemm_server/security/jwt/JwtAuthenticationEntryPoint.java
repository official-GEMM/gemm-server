package com.example.gemm_server.security.jwt;

import com.example.gemm_server.dto.EmptyDataResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

  @Override
  public void commence(
      HttpServletRequest request,
      HttpServletResponse response,
      AuthenticationException authException
  ) throws IOException {
    EmptyDataResponse.setJsonResponse(response, HttpServletResponse.SC_UNAUTHORIZED,
        authException.getMessage());
  }
}
