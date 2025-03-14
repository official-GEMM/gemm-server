package com.example.gemm_server.config;

import com.example.gemm_server.common.annotation.auth.AuthInterceptor;
import com.example.gemm_server.common.annotation.auth.AuthorizeOwnerInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@RequiredArgsConstructor
@Configuration
public class WebConfig implements WebMvcConfigurer {

  private final AuthInterceptor authInterceptor;
  private final AuthorizeOwnerInterceptor authorizeOwnerInterceptor;

  @Override
  public void addInterceptors(InterceptorRegistry registry) {
    registry.addInterceptor(authInterceptor);
    registry.addInterceptor(authorizeOwnerInterceptor);
  }
}