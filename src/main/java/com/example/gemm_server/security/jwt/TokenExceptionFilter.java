package com.example.gemm_server.security.jwt;

import com.example.gemm_server.dto.EmptyDataResponse;
import com.example.gemm_server.exception.TokenException;
import io.micrometer.common.lang.NonNull;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.springframework.web.filter.OncePerRequestFilter;

public class TokenExceptionFilter extends OncePerRequestFilter {

  @Override
  protected void doFilterInternal(@NonNull HttpServletRequest request,
      @NonNull HttpServletResponse response,
      FilterChain filterChain) throws ServletException, IOException {

    try {
      filterChain.doFilter(request, response);
    } catch (TokenException e) {
      EmptyDataResponse.setJsonResponse(response, e.getStatusCode(), e.getMessage());
    } catch (Exception e) {
      EmptyDataResponse.setJsonResponse(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
          e.getMessage());
    }
  }
}