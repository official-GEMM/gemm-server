package com.example.gemm_server.controller;

import static com.example.gemm_server.common.code.success.GenerationSuccessCode.ACTIVITY_GENERATION_DELETED;
import static com.example.gemm_server.common.code.success.GenerationSuccessCode.GUIDE_GENERATION_DELETED;

import com.example.gemm_server.common.annotation.auth.BearerAuth;
import com.example.gemm_server.common.annotation.belong.GenerationBelonging;
import com.example.gemm_server.common.constant.Policy;
import com.example.gemm_server.domain.entity.Generation;
import com.example.gemm_server.domain.entity.Material;
import com.example.gemm_server.dto.CommonResponse;
import com.example.gemm_server.dto.EmptyDataResponse;
import com.example.gemm_server.dto.common.PageInfo;
import com.example.gemm_server.dto.common.response.DownloadMaterialResponse;
import com.example.gemm_server.dto.storage.GenerationWithThumbnail;
import com.example.gemm_server.dto.storage.response.GetGeneratedActivitiesResponse;
import com.example.gemm_server.dto.storage.response.GetGeneratedActivityDetailResponse;
import com.example.gemm_server.dto.storage.response.GetGeneratedGuideDetailResponse;
import com.example.gemm_server.dto.storage.response.GetGeneratedGuidesResponse;
import com.example.gemm_server.dto.storage.response.GetPurchasedActivitiesResponse;
import com.example.gemm_server.dto.storage.response.GetPurchasedActivityDetailResponse;
import com.example.gemm_server.security.jwt.CustomUser;
import com.example.gemm_server.service.GenerationService;
import com.example.gemm_server.service.MaterialService;
import com.example.gemm_server.service.ThumbnailService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@BearerAuth
@RequiredArgsConstructor
@RestController()
@RequestMapping("/my/storage")
@Tag(name = "My Storage", description = "사용자 저장소 관리 API")
public class StorageController {

  private final GenerationService generationService;
  private final MaterialService materialService;
  private final ThumbnailService thumbnailService;

  @Operation(summary = "생성한 활동 방법 리스트 조회", description = "사용자가 생성한 활동 방법 리스트를 조회하는 API")
  @GetMapping("/generate/guides")
  public ResponseEntity<CommonResponse<GetGeneratedGuidesResponse>> getGeneratedGuides(
      @AuthenticationPrincipal CustomUser user,
      @Param("page") Integer page
  ) {
    Page<Generation> guides = generationService.getGenerationsHasNoMaterialByMemberIdAndPage(
        user.getId(), page, Policy.STORAGE_LIMIT_LONG);
    PageInfo pageInfo = new PageInfo(page, guides.getTotalPages());

    GetGeneratedGuidesResponse response =
        new GetGeneratedGuidesResponse(guides.getContent(), pageInfo);
    return ResponseEntity.ok(new CommonResponse<>(response));
  }

  @Operation(summary = "생성한 활동 리스트 조회", description = "사용자가 생성한 활동 리스트를 조회하는 API")
  @GetMapping("/generate/activities")
  public ResponseEntity<CommonResponse<GetGeneratedActivitiesResponse>> getGeneratedActivities(
      @AuthenticationPrincipal CustomUser user,
      @Param("page") Integer page
  ) {
    Page<Generation> activities = generationService.getGenerationsHasMaterialByMemberIdAndPage(
        user.getId(), page, Policy.STORAGE_LIMIT_LONG);
    PageInfo pageInfo = new PageInfo(page, activities.getTotalPages());

    List<GenerationWithThumbnail> generationWithThumbnails =
        thumbnailService.getMainThumbnailForEachGeneration(activities.getContent());

    GetGeneratedActivitiesResponse response =
        new GetGeneratedActivitiesResponse(generationWithThumbnails, pageInfo);
    return ResponseEntity.ok(new CommonResponse<>(response));
  }

  @GenerationBelonging()
  @Operation(summary = "생성한 활동 방법 상세", description = "사용자가 생성한 활동 방법 상세를 조회하는 API")
  @GetMapping("/generate/guides/{generationId}")
  public ResponseEntity<CommonResponse<GetGeneratedGuideDetailResponse>> getGeneratedGuideDetail(
      @PathVariable("generationId") Long generationId
  ) {
    Generation generation = generationService.getGenerationOrThrow(generationId);

    GetGeneratedGuideDetailResponse response = new GetGeneratedGuideDetailResponse(generation);
    return ResponseEntity.ok(new CommonResponse<>(response));
  }

  @GenerationBelonging()
  @Operation(summary = "생성한 활동 상세", description = "사용자가 생성한 활동 상세를 조회하는 API")
  @GetMapping("/generate/activities/{generationId}")
  public ResponseEntity<CommonResponse<GetGeneratedActivityDetailResponse>> getGeneratedActivityDetail(
      @PathVariable("generationId") Long generationId
  ) {
    Generation generation = generationService.getGenerationOrThrow(generationId);

    List<Material> materials = materialService.getMaterialsWithThumbnailByGenerationId(
        generationId);
    GetGeneratedActivityDetailResponse response = new GetGeneratedActivityDetailResponse(
        generation, materials);
    return ResponseEntity.ok(new CommonResponse<>(response));
  }

  @GenerationBelonging()
  @Operation(summary = "생성한 활동 방법 삭제", description = "사용자가 생성한 활동 방법을 삭제하는 API")
  @DeleteMapping("/generate/guides/{generationId}")
  public ResponseEntity<EmptyDataResponse> deleteGeneratedGuide(
      @PathVariable("generationId") Long generationId
  ) {
    generationService.deleteGeneration(generationId);
    return ResponseEntity.ok(new EmptyDataResponse(GUIDE_GENERATION_DELETED));
  }

  @GenerationBelonging()
  @Operation(summary = "생성한 활동 삭제", description = "사용자가 생성한 활동을 삭제하는 API")
  @DeleteMapping("/generate/activities/{generationId}")
  public ResponseEntity<EmptyDataResponse> deleteGeneratedActivity(
      @PathVariable("generationId") Long generationId
  ) {
    generationService.deleteGeneration(generationId);
    return ResponseEntity.ok(new EmptyDataResponse(ACTIVITY_GENERATION_DELETED));
  }

  // 미완성 API
  @Operation(summary = "생성한 활동 자료 다운로드", description = "사용자가 생성한 활동의 자료를 다운로드하는 API")
  @GetMapping("/generate/activities/{generationId}/download")
  public ResponseEntity<DownloadMaterialResponse> downloadGeneratedActivityMaterial(
      @PathVariable("generationId") Long generationId
  ) {
    DownloadMaterialResponse response = new DownloadMaterialResponse();
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
      @PathVariable("dealId") Long dealId
  ) {
    GetPurchasedActivityDetailResponse response = new GetPurchasedActivityDetailResponse();
    return ResponseEntity.ok(new CommonResponse<>(response));
  }

  @Operation(summary = "구매 자료 다운로드", description = "사용자가 구매한 활동의 자료를 다운로드하는 API")
  @GetMapping("/generate/purchases/{dealId}/download")
  public ResponseEntity<DownloadMaterialResponse> downloadPurchasedActivityMaterial(
      @PathVariable("dealId") Long dealId
  ) {
    DownloadMaterialResponse response = new DownloadMaterialResponse();
    return ResponseEntity.ok(response);
  }
}
