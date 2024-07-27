package com.example.gemm_server.controller;

import com.example.gemm_server.common.annotation.auth.BearerAuth;
import com.example.gemm_server.dto.CommonResponse;
import com.example.gemm_server.dto.EmptyDataResponse;
import com.example.gemm_server.dto.common.DownloadMaterial;
import com.example.gemm_server.dto.storage.response.GetGeneratedActivitiesResponse;
import com.example.gemm_server.dto.storage.response.GetGeneratedActivityDetailResponse;
import com.example.gemm_server.dto.storage.response.GetGeneratedGuideDetailResponse;
import com.example.gemm_server.dto.storage.response.GetGeneratedGuidesResponse;
import com.example.gemm_server.dto.storage.response.GetPurchasedActivitiesResponse;
import com.example.gemm_server.dto.storage.response.GetPurchasedActivityDetailResponse;
import com.example.gemm_server.dto.storage.response.GetStorageResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.websocket.server.PathParam;
import lombok.RequiredArgsConstructor;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@BearerAuth
@RequiredArgsConstructor
@RestController()
@RequestMapping("/my/storage")
@Tag(name = "My Storage", description = "사용자 저장소 관리 API")
public class StorageController {

  // 미완성 API
  @Operation(summary = "내 저장소 조회", description = "사용자의 저장소를 조회하는 API")
  @GetMapping()
  public ResponseEntity<CommonResponse<GetStorageResponse>> getStorage() {
    GetStorageResponse response = new GetStorageResponse();
    return ResponseEntity.ok(new CommonResponse<>(response));
  }

  @Operation(summary = "생성한 활동 방법 리스트 조회", description = "사용자가 생성한 활동 방법 리스트를 조회하는 API")
  @GetMapping("/generate/guides")
  public ResponseEntity<CommonResponse<GetGeneratedGuidesResponse>> getGeneratedGuides(
      @Param("page") Integer page
  ) {
    GetGeneratedGuidesResponse response = new GetGeneratedGuidesResponse();
    return ResponseEntity.ok(new CommonResponse<>(response));
  }

  @Operation(summary = "생성한 활동 방법 상세", description = "사용자가 생성한 활동 방법 상세를 조회하는 API")
  @GetMapping("/generate/guides/{generationId}")
  public ResponseEntity<CommonResponse<GetGeneratedGuideDetailResponse>> getGeneratedGuideDetail(
      @PathParam("generationId") Long generationId
  ) {
    GetGeneratedGuideDetailResponse response = new GetGeneratedGuideDetailResponse();
    return ResponseEntity.ok(new CommonResponse<>(response));
  }

  @Operation(summary = "생성한 활동 방법 삭제", description = "사용자가 생성한 활동 방법을 삭제하는 API")
  @DeleteMapping("/generate/guides/{generationId}")
  public ResponseEntity<EmptyDataResponse> deleteGeneratedGuide(
      @PathParam("generationId") Long generationId
  ) {
    return ResponseEntity.ok(new EmptyDataResponse());
  }

  @Operation(summary = "생성한 활동 리스트 조회", description = "사용자가 생성한 활동 리스트를 조회하는 API")
  @GetMapping("/generate/activities")
  public ResponseEntity<CommonResponse<GetGeneratedActivitiesResponse>> getGeneratedActivities(
      @Param("page") Integer page
  ) {
    GetGeneratedActivitiesResponse response = new GetGeneratedActivitiesResponse();
    return ResponseEntity.ok(new CommonResponse<>(response));
  }

  @Operation(summary = "생성한 활동 상세", description = "사용자가 생성한 활동 상세를 조회하는 API")
  @GetMapping("/generate/activities/{generationId}")
  public ResponseEntity<CommonResponse<GetGeneratedActivityDetailResponse>> getGeneratedActivityDetail(
      @PathParam("generationId") Long generationId
  ) {
    GetGeneratedActivityDetailResponse response = new GetGeneratedActivityDetailResponse();
    return ResponseEntity.ok(new CommonResponse<>(response));
  }

  @Operation(summary = "생성한 활동 삭제", description = "사용자가 생성한 활동을 삭제하는 API")
  @DeleteMapping("/generate/activities/{generationId}")
  public ResponseEntity<EmptyDataResponse> deleteGeneratedActivity(
      @PathParam("generationId") Long generationId
  ) {
    return ResponseEntity.ok(new EmptyDataResponse());
  }

  @Operation(summary = "생성한 활동 자료 다운로드", description = "사용자가 생성한 활동의 자료를 다운로드하는 API")
  @GetMapping("/generate/activities/{generationId}/download")
  public ResponseEntity<DownloadMaterial> downloadGeneratedActivityMaterial(
      @PathParam("generationId") Long generationId
  ) {
    DownloadMaterial response = new DownloadMaterial();
    return ResponseEntity.ok(response);
  }

  @Operation(summary = "구매한 활동 리스트 조회", description = "사용자가 구매한 활동 리스트를 조회하는 API")
  @GetMapping("/generate/purchases")
  public ResponseEntity<CommonResponse<GetPurchasedActivitiesResponse>> getPurchasedActivities(
      @Param("page") Integer page
  ) {
    GetPurchasedActivitiesResponse response = new GetPurchasedActivitiesResponse();
    return ResponseEntity.ok(new CommonResponse<>(response));
  }

  @Operation(summary = "구매한 활동 상세", description = "사용자가 구매한 활동 상세를 조회하는 API")
  @GetMapping("/generate/purchases/{dealId}")
  public ResponseEntity<CommonResponse<GetPurchasedActivityDetailResponse>> getPurchasedActivityDetail(
      @PathParam("dealId") Long dealId
  ) {
    GetPurchasedActivityDetailResponse response = new GetPurchasedActivityDetailResponse();
    return ResponseEntity.ok(new CommonResponse<>(response));
  }

  @Operation(summary = "구매 자료 다운로드", description = "사용자가 구매한 활동의 자료를 다운로드하는 API")
  @GetMapping("/generate/purchases/{dealId}/download")
  public ResponseEntity<DownloadMaterial> downloadPurchasedActivityMaterial(
      @PathParam("dealId") Long dealId
  ) {
    DownloadMaterial response = new DownloadMaterial();
    return ResponseEntity.ok(response);
  }
}
