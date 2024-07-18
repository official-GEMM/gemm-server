package com.example.gemm_server.controller;

import com.example.gemm_server.domain.entity.Member;
import com.example.gemm_server.dto.CommonResponse;
import com.example.gemm_server.dto.auth.LoginResponse;
import com.example.gemm_server.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController()
@RequestMapping("/auth")
public class AuthController {

  private final AuthService authService;

  @GetMapping("/login/{userId}")
  public ResponseEntity<CommonResponse> login(@Param("accessToken") String accessToken,
      @Param("refreshToken") String refreshToken,
      @PathVariable Long userId) {
    Member member = authService.compensateMemberForDailyAttendance(userId);
    LoginResponse loginResponse = new LoginResponse(accessToken, refreshToken,
        member.isDataCompleted());
    return CommonResponse.okResponseEntity(loginResponse);
  }
}
