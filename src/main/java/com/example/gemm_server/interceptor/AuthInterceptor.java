package com.example.gemm_server.interceptor;

import static com.example.gemm_server.security.jwt.TokenProvider.AUTHORIZATION;

import com.example.gemm_server.common.annotation.Admin;
import com.example.gemm_server.common.annotation.BearerAuth;
import com.example.gemm_server.common.enums.Role;
import com.example.gemm_server.dto.EmptyDataResponse;
import com.example.gemm_server.exception.MemberException;
import io.micrometer.common.lang.NonNull;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class AuthInterceptor implements HandlerInterceptor {

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
      EmptyDataResponse.setJsonResponse(response, e.getStatusCode(), e.getMessage());
      return false;
    }
  }

  private void interceptBearerAuth(HandlerMethod handlerMethod, HttpServletRequest request) {
    BearerAuth bearerAuth = handlerMethod.getMethodAnnotation(BearerAuth.class);
    if (bearerAuth == null) {
      return;
    }

    String bearerToken = request.getHeader(AUTHORIZATION);
    if (!StringUtils.hasText(bearerToken)) { // 헤더에 토큰이 있을 경우, 필터에서 유효하지 않은 토큰에 대해 예외처리를 해준다.
      throw new MemberException(bearerAuth.errorCode());
    }
  }

  private void interceptAuthority(HandlerMethod handlerMethod) {
    Admin admin = handlerMethod.getMethodAnnotation(Admin.class);
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
