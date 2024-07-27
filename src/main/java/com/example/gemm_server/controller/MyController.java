package com.example.gemm_server.controller;

import com.example.gemm_server.common.annotation.auth.BearerAuth;
import com.example.gemm_server.domain.entity.Member;
import com.example.gemm_server.dto.CommonResponse;
import com.example.gemm_server.dto.EmptyDataResponse;
import com.example.gemm_server.dto.my.request.UpdateMyInformationRequest;
import com.example.gemm_server.dto.my.request.UpdateProfileImageRequest;
import com.example.gemm_server.dto.my.response.GetGemResponse;
import com.example.gemm_server.dto.my.response.GetHeaderResponse;
import com.example.gemm_server.dto.my.response.GetMyInformationResponse;
import com.example.gemm_server.dto.my.response.GetMyNotificationsResponse;
import com.example.gemm_server.dto.my.response.GetMyPurchasesResponse;
import com.example.gemm_server.dto.my.response.GetMySalesResponse;
import com.example.gemm_server.dto.my.response.GetMyScrapsResponse;
import com.example.gemm_server.dto.my.response.UpdateMyInformationResponse;
import com.example.gemm_server.dto.my.response.UpdateProfileImageResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.repository.query.Param;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@BearerAuth
@RequiredArgsConstructor
@RestController()
@RequestMapping("/my")
@Tag(name = "My", description = "사용자 정보 관리 API")
public class MyController {

  // 미완성 API
  @Operation(summary = "내 정보 조회", description = "로그인한 사용자의 정보를 가져오는 API")
  @GetMapping()
  public ResponseEntity<CommonResponse<GetMyInformationResponse>> getMyInformation() {
    GetMyInformationResponse response = new GetMyInformationResponse(new Member());
    return ResponseEntity.ok(new CommonResponse<>(response));
  }

  @Operation(summary = "내 정보 변경", description = "로그인한 사용자의 관리 연령과 휴대전화 번호를 변경하는 API")
  @PutMapping()
  public ResponseEntity<CommonResponse<UpdateMyInformationResponse>> updateMyInformation(
      @Valid @RequestBody UpdateMyInformationRequest request
  ) {
    UpdateMyInformationResponse response = new UpdateMyInformationResponse(new Member());
    return ResponseEntity.ok(new CommonResponse<>(response));
  }

  @Operation(summary = "회원 탈퇴", description = "회원 탈퇴 API")
  @DeleteMapping()
  public ResponseEntity<EmptyDataResponse> withdraw() {
    return ResponseEntity.ok(new EmptyDataResponse());
  }

  @Operation(summary = "알림 조회", description = "사용자의 알림을 가져오는 API")
  @GetMapping("/notifications")
  public ResponseEntity<CommonResponse<GetMyNotificationsResponse[]>> getMyNotifications() {
    GetMyNotificationsResponse[] response = {};
    return ResponseEntity.ok(new CommonResponse<>(response));
  }

  @Operation(summary = "프로필 이미지 변경", description = "사용자의 프로필 이미지를 변경하는 API")
  @PutMapping(value = "/profile-image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
  public ResponseEntity<CommonResponse<UpdateProfileImageResponse>> getMyNotifications(
      @Valid @RequestBody UpdateProfileImageRequest request
  ) {
    UpdateProfileImageResponse response = new UpdateProfileImageResponse();
    return ResponseEntity.ok(new CommonResponse<>(response));
  }

  @Operation(summary = "내 스크랩 조회", description = "사용자의 스크랩 리스트를 가져오는 API")
  @GetMapping("/scraps")
  public ResponseEntity<CommonResponse<GetMyScrapsResponse>> getMyScraps(
      @Param("page") Integer page
  ) {
    GetMyScrapsResponse response = new GetMyScrapsResponse();
    return ResponseEntity.ok(new CommonResponse<>(response));
  }

  @Operation(summary = "내 구매 내역 조회", description = "사용자의 마켓 상품 구매 내역을 가져오는 API")
  @GetMapping("/history/purchases")
  public ResponseEntity<CommonResponse<GetMyPurchasesResponse>> getMyPurchases(
      @Param("page") Integer page,
      @Param("year") Integer year,
      @Param("month") Short month
  ) {
    GetMyPurchasesResponse response = new GetMyPurchasesResponse();
    return ResponseEntity.ok(new CommonResponse<>(response));
  }

  @Operation(summary = "내 판매 내역 조회", description = "사용자의 마켓 상품 판매 내역을 가져오는 API")
  @GetMapping("/history/sales")
  public ResponseEntity<CommonResponse<GetMySalesResponse>> getMySales(
      @Param("page") Integer page,
      @Param("year") Integer year,
      @Param("month") Short month
  ) {
    GetMySalesResponse response = new GetMySalesResponse();
    return ResponseEntity.ok(new CommonResponse<>(response));
  }

  @Operation(summary = "내 보유 젬 조회", description = "사용자의 보유한 젬의 수를 조회하는 API")
  @GetMapping("/gem")
  public ResponseEntity<CommonResponse<GetGemResponse>> getGemCount() {
    GetGemResponse response = new GetGemResponse();
    return ResponseEntity.ok(new CommonResponse<>(response));
  }

  @Operation(summary = "헤더 정보 조회", description = "헤더에 필요한 사용자의 정보를 가져오는 API")
  @GetMapping("/header")
  public ResponseEntity<CommonResponse<GetHeaderResponse>> getHeaderInformation() {
    GetHeaderResponse response = new GetHeaderResponse();
    return ResponseEntity.ok(new CommonResponse<>(response));
  }
}
