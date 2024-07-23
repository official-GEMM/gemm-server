package com.example.gemm_server.security.jwt;

import com.example.gemm_server.dto.CommonResponse;
import com.example.gemm_server.exception.TokenException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.springframework.web.filter.OncePerRequestFilter;

public class TokenExceptionFilter extends OncePerRequestFilter {

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
      FilterChain filterChain) throws ServletException, IOException {

    try {
      filterChain.doFilter(request, response);
    } catch (TokenException error) {
      CommonResponse.setJsonResponse(response, error.getStatusCode(), error.getMessage());
    }
  }
}