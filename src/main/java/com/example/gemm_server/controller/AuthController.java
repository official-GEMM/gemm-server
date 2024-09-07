package com.example.gemm_server.controller;

import static com.example.gemm_server.common.code.success.MemberSuccessCode.MEMBER_LOGOUT;
import static com.example.gemm_server.common.code.success.MemberSuccessCode.MEMBER_UPDATED;
import static com.example.gemm_server.common.code.success.MemberSuccessCode.SEND_PHONE_VERIFICATION_CODE;

import com.example.gemm_server.common.annotation.auth.BearerAuth;
import com.example.gemm_server.common.util.CookieUtil;
import com.example.gemm_server.dto.CommonResponse;
import com.example.gemm_server.dto.EmptyDataResponse;
import com.example.gemm_server.dto.auth.Token;
import com.example.gemm_server.dto.auth.request.CheckNicknameDuplicationRequest;
import com.example.gemm_server.dto.auth.request.CheckPhoneVerificationCodeRequest;
import com.example.gemm_server.dto.auth.request.PostNecessaryMemberDataRequest;
import com.example.gemm_server.dto.auth.request.SendPhoneVerificationCodeRequest;
import com.example.gemm_server.dto.auth.response.CheckNicknameDuplicationResponse;
import com.example.gemm_server.dto.auth.response.ReissueResponse;
import com.example.gemm_server.security.jwt.CustomUser;
import com.example.gemm_server.security.jwt.TokenProvider;
import com.example.gemm_server.service.AuthService;
import com.example.gemm_server.service.MemberService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

@RequiredArgsConstructor
@RestController()
@RequestMapping("/auth")
@Tag(name = "Auth", description = "사용자 인증/인가 API")
public class AuthController {

  private final AuthService authService;
  private final TokenProvider tokenProvider;
  private final MemberService memberService;
  private final CookieUtil cookieUtil;

  @Value("${login.redirect.url}")
  private String loginRedirectUrl;

  @Operation(summary = "소셜 로그인", description = "소셜 로그인 처리 이후 redirect되는 API")
  @GetMapping("/login")
  public void login(
      @CookieValue(value = "refreshToken") Cookie refreshTokenCookie,
      HttpServletResponse response
  ) throws IOException {
    String refreshToken = refreshTokenCookie.getValue();
    Long userId = tokenProvider.getUserIdFromToken(refreshToken);
    boolean isAttendanceCompensated = authService.compensateMemberForDailyAttendance(userId);
    String redirectWithParams = UriComponentsBuilder.fromUriString(loginRedirectUrl)
        .queryParam("isAttendanceCompensated", isAttendanceCompensated)
        .toUriString();

    response.sendRedirect(redirectWithParams);
  }

  @BearerAuth
  @Operation(summary = "로그아웃", description = "로그아웃 API")
  @PostMapping("/logout")
  public ResponseEntity<EmptyDataResponse> logout(
      HttpServletRequest request,
      @AuthenticationPrincipal CustomUser user
  ) {
    String token = tokenProvider.resolveToken(request);
    authService.logout(user.getId(), token);
    return ResponseEntity.ok(new EmptyDataResponse(MEMBER_LOGOUT));
  }

  @BearerAuth
  @Operation(summary = "필수 정보 입력", description = "필수 정보를 입력하지 않은 사용자의 정보를 업데이트하는 API")
  @PostMapping("/profile")
  public ResponseEntity<EmptyDataResponse> updateNecessaryMemberInformation(
      @Valid @RequestBody PostNecessaryMemberDataRequest memberNecessaryData,
      @AuthenticationPrincipal CustomUser user
  ) {
    String referralCode = memberNecessaryData.getReferralCode();
    if (referralCode != null) {
      authService.compensateMemberForReferralIfValid(user.getId(), referralCode);
    }
    memberService.updateNecessaryMemberData(user.getId(), memberNecessaryData);
    return ResponseEntity.ok(new EmptyDataResponse(MEMBER_UPDATED));
  }

  @Operation(summary = "토큰 갱신", description = "accessToken과 refreshToken을 재발급하는 API")
  @PatchMapping("/reissue")
  public ResponseEntity<CommonResponse<ReissueResponse>> reissueAccessToken(
      @CookieValue(value = "refreshToken") Cookie refreshTokenCookie,
      HttpServletResponse response) {
    Token tokens = tokenProvider.reissue(refreshTokenCookie.getValue());
    ResponseCookie newRefreshTokenCookie = cookieUtil.createForRefreshToken(
        tokens.getRefreshToken());

    response.addHeader("Set-Cookie", newRefreshTokenCookie.toString());
    ReissueResponse reissueResponse = new ReissueResponse(tokens.getAccessToken());
    return ResponseEntity.ok(new CommonResponse<>(reissueResponse));
  }

  @Operation(summary = "닉네임 중복 확인", description = "닉네임 중복 여부를 확인하는 API")
  @PutMapping("/nickname/duplicate")
  public ResponseEntity<CommonResponse<CheckNicknameDuplicationResponse>> checkNicknameDuplication(
      @Valid @RequestBody CheckNicknameDuplicationRequest nicknameDuplicationRequest) {
    String nickname = nicknameDuplicationRequest.getNickname();
    boolean isNicknameDuplicated = memberService.isNicknameExists(nickname);
    CheckNicknameDuplicationResponse response = new CheckNicknameDuplicationResponse(
        isNicknameDuplicated);
    return ResponseEntity.ok(new CommonResponse<>(response));
  }

  // 미완성 API
  @BearerAuth
  @Operation(summary = "휴대폰 인증번호 전송", description = "사용자의 휴대전화를 인증할 수 있는 코드를 전송하는 API")
  @PostMapping("/verify/phone")
  public ResponseEntity<EmptyDataResponse> sendPhoneVerificationCode(
      @Valid @RequestBody SendPhoneVerificationCodeRequest request) {
    return ResponseEntity.ok(new EmptyDataResponse(SEND_PHONE_VERIFICATION_CODE));
  }

  @Operation(summary = "휴대폰 인증번호 확인", description = "사용자의 휴대전화를 인증할 수 있는 코드를 확인하는 API")
  @PutMapping("/verify/phone")
  public ResponseEntity<EmptyDataResponse> checkPhoneVerificationCode(
      @Valid @RequestBody CheckPhoneVerificationCodeRequest request) {
    return ResponseEntity.ok(new EmptyDataResponse());
  }
}
