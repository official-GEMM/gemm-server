package com.example.gemm_server.common.annotation.auth;

import com.example.gemm_server.common.enums.Role;
import com.example.gemm_server.dto.ErrorResponse;
import com.example.gemm_server.exception.MemberException;
import com.example.gemm_server.security.jwt.TokenProvider;
import io.micrometer.common.lang.NonNull;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

@RequiredArgsConstructor
@Component
public class AuthInterceptor implements HandlerInterceptor {

  private final TokenProvider tokenProvider;

  @Override
  public boolean preHandle(
      @NonNull HttpServletRequest request,
      @NonNull HttpServletResponse response,
      @NonNull Object handler
  ) throws Exception {
    if (!(handler instanceof HandlerMethod handlerMethod)) {
      return true;
    }

    try {
      interceptBearerAuth(handlerMethod, request);
      interceptAuthority(handlerMethod);
      return true;
    } catch (MemberException e) {
      ErrorResponse.setJsonResponse(response, e.getStatusCode(), e.getMessage());
      return false;
    }
  }

  private void interceptBearerAuth(HandlerMethod handlerMethod, HttpServletRequest request) {
    BearerAuth bearerAuth = Optional.ofNullable(handlerMethod.getMethodAnnotation(BearerAuth.class))
        .orElse(handlerMethod.getBeanType().getAnnotation(BearerAuth.class));
    if (bearerAuth == null) {
      return;
    }

    String bearerToken = tokenProvider.resolveToken(request);
    if (!tokenProvider.validateToken(bearerToken)) {
      throw new MemberException(bearerAuth.errorCode());
    }
  }

  private void interceptAuthority(HandlerMethod handlerMethod) {
    Admin admin = Optional.ofNullable(handlerMethod.getMethodAnnotation(Admin.class))
        .orElse(handlerMethod.getBeanType().getAnnotation(Admin.class));
    if (admin == null) {
      return;
    }

    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    boolean isAdmin = authentication.getAuthorities()
        .contains(new SimpleGrantedAuthority(Role.ADMIN.getName()));
    if (!isAdmin) {
      throw new MemberException(admin.errorCode());
    }
  }
}
