package com.example.gemm_server.controller;

import static com.example.gemm_server.common.code.success.MemberSuccessCode.MEMBER_LOGOUT;
import static com.example.gemm_server.common.code.success.MemberSuccessCode.MEMBER_UPDATED;

import com.example.gemm_server.common.annotation.BearerAuth;
import com.example.gemm_server.domain.entity.Member;
import com.example.gemm_server.dto.CommonResponse;
import com.example.gemm_server.dto.EmptyDataResponse;
import com.example.gemm_server.dto.auth.LoginResponse;
import com.example.gemm_server.dto.auth.TokenResponse;
import com.example.gemm_server.dto.member.NecessaryMemberDataPostRequest;
import com.example.gemm_server.security.jwt.CustomUser;
import com.example.gemm_server.security.jwt.TokenProvider;
import com.example.gemm_server.service.AuthService;
import com.example.gemm_server.service.MemberService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.Cookie;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController()
@RequestMapping("/auth")
@Tag(name = "Auth", description = "사용자 인증/인가 API")
public class AuthController {

  private final AuthService authService;
  private final TokenProvider tokenProvider;
  private final MemberService memberService;

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

  @BearerAuth
  @Operation(summary = "로그아웃", description = "로그아웃 API")
  @PostMapping("/logout")
  public ResponseEntity<EmptyDataResponse> logout(
      @AuthenticationPrincipal CustomUser user
  ) {
    authService.logout(user.getId());
    return ResponseEntity.ok(new EmptyDataResponse(MEMBER_LOGOUT));
  }

  @BearerAuth
  @Operation(summary = "필수 정보 입력", description = "필수 정보를 입력하지 않은 사용자의 정보를 업데이트하는 API")
  @PostMapping("/profile")
  public ResponseEntity<EmptyDataResponse> updateNecessaryMemberInformation(
      @Valid @RequestBody NecessaryMemberDataPostRequest memberNecessaryData,
      @AuthenticationPrincipal CustomUser user
  ) {
    String referralCode = memberNecessaryData.getReferralCode();
    if (referralCode != null) {
      authService.compensateMemberForReferral(user.getId(), referralCode);
    }
    memberService.updateNecessaryMemberData(user.getId(), memberNecessaryData);
    return ResponseEntity.ok(new EmptyDataResponse(MEMBER_UPDATED));
  }

  @Operation(summary = "토큰 갱신", description = "accessToken과 refreshToken을 재발급하는 API")
  @PatchMapping("/reissue")
  public ResponseEntity<CommonResponse<TokenResponse>> reissueAccessToken(
      @CookieValue(value = "refreshToken") Cookie refreshToken) {
    TokenResponse tokens = tokenProvider.reissueAccessToken(refreshToken.getValue());
    return ResponseEntity.ok(new CommonResponse<>(tokens));
  }
}
