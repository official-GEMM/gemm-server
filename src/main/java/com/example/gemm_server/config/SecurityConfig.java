package com.example.gemm_server.config;

import com.example.gemm_server.security.jwt.JwtAccessDeniedHandler;
import com.example.gemm_server.security.jwt.JwtAuthenticationEntryPoint;
import com.example.gemm_server.security.jwt.TokenAuthenticationFilter;
import com.example.gemm_server.security.jwt.TokenExceptionFilter;
import com.example.gemm_server.security.oauth2.CustomOAuth2UserService;
import com.example.gemm_server.security.oauth2.OAuth2FailureHandler;
import com.example.gemm_server.security.oauth2.OAuth2SuccessHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

  // TODO: 인증 인가 경로 설정
  private static final AntPathRequestMatcher path = new AntPathRequestMatcher("/**");
  private final OAuth2SuccessHandler oAuth2SuccessHandler;
  private final OAuth2FailureHandler oAuth2FailureHandler;
  private final TokenAuthenticationFilter tokenAuthenticationFilter;
  private final CustomOAuth2UserService customOAuth2UserService;

  @Bean
  protected SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http
        // rest api 설정
        .csrf(AbstractHttpConfigurer::disable)
        .httpBasic(AbstractHttpConfigurer::disable)
        .formLogin(AbstractHttpConfigurer::disable)
        .logout(AbstractHttpConfigurer::disable)
        .sessionManagement(c ->
            c.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

        // request 인증, 인가 설정
        .authorizeHttpRequests(request ->
            request.requestMatchers(path).permitAll()
                .anyRequest().authenticated()
        )
        .oauth2Login(oauth2 -> oauth2
            .userInfoEndpoint(userInfo -> userInfo
                .userService(customOAuth2UserService)
            )
            .successHandler(oAuth2SuccessHandler)
            .failureHandler(oAuth2FailureHandler)
        )

        // jwt 관련 설정
        .addFilterBefore(tokenAuthenticationFilter,
            UsernamePasswordAuthenticationFilter.class)
        .addFilterBefore(new TokenExceptionFilter(),
            tokenAuthenticationFilter.getClass())

        // 인증 예외 핸들링
        .exceptionHandling((exceptions) -> exceptions
            .authenticationEntryPoint(new JwtAuthenticationEntryPoint())
            .accessDeniedHandler(new JwtAccessDeniedHandler()))
    ;

    return http.build();
  }
}