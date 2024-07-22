package com.example.gemm_server.controller;

import com.example.gemm_server.domain.entity.Member;
import com.example.gemm_server.dto.CommonResponse;
import com.example.gemm_server.dto.auth.LoginResponse;
import com.example.gemm_server.security.jwt.TokenProvider;
import com.example.gemm_server.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController()
@RequestMapping("/auth")
@Tag(name = "Auth", description = "사용자 인증/인가 API")
public class AuthController {

  private final AuthService authService;
  private final TokenProvider tokenProvider;

  @Operation(summary = "소셜 로그인", description = "소셜 로그인 처리 이후 redirect되는 API")
  @GetMapping("/login")
  public ResponseEntity<CommonResponse<LoginResponse>> login(
      @Param("accessToken") String accessToken,
      @Param("refreshToken") String refreshToken
  ) {
    Long userId = tokenProvider.getUserIdFromToken(accessToken);
    Member member = authService.compensateMemberForDailyAttendance(userId);

    LoginResponse loginResponse = new LoginResponse(accessToken, refreshToken,
        member.isDataCompleted());
    return ResponseEntity.ok(new CommonResponse<>(loginResponse));
  }
}
