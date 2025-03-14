package com.example.gemm_server.controller;

import static com.example.gemm_server.common.code.success.GenerationSuccessCode.ACTIVITY_GENERATION_DELETED;
import static com.example.gemm_server.common.code.success.GenerationSuccessCode.GUIDE_GENERATION_DELETED;

import com.example.gemm_server.common.annotation.auth.AuthorizeOwner;
import com.example.gemm_server.common.annotation.auth.BearerAuth;
import com.example.gemm_server.common.constant.Policy;
import com.example.gemm_server.domain.entity.Deal;
import com.example.gemm_server.domain.entity.Generation;
import com.example.gemm_server.domain.entity.Material;
import com.example.gemm_server.dto.CommonResponse;
import com.example.gemm_server.dto.EmptyDataResponse;
import com.example.gemm_server.dto.common.PageInfo;
import com.example.gemm_server.dto.common.response.DownloadMaterialResponse;
import com.example.gemm_server.dto.storage.DealBundle;
import com.example.gemm_server.dto.storage.GenerationBundle;
import com.example.gemm_server.dto.storage.response.GetGeneratedActivitiesResponse;
import com.example.gemm_server.dto.storage.response.GetGeneratedActivityDetailResponse;
import com.example.gemm_server.dto.storage.response.GetGeneratedGuideDetailResponse;
import com.example.gemm_server.dto.storage.response.GetGeneratedGuidesResponse;
import com.example.gemm_server.dto.storage.response.GetPurchasedActivitiesResponse;
import com.example.gemm_server.dto.storage.response.GetPurchasedActivityDetailResponse;
import com.example.gemm_server.security.jwt.CustomUser;
import com.example.gemm_server.service.DealService;
import com.example.gemm_server.service.GenerationService;
import com.example.gemm_server.service.MarketItemService;
import com.example.gemm_server.service.MaterialService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Min;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@BearerAuth
@Validated
@RequiredArgsConstructor
@RestController()
@RequestMapping("/my/storage")
@Tag(name = "My Storage", description = "사용자 저장소 관리 API")
public class StorageController {

  private final GenerationService generationService;
  private final MaterialService materialService;
  private final DealService dealService;
  private final MarketItemService marketItemService;

  @Operation(summary = "생성한 활동 방법 리스트 조회", description = "사용자가 생성한 활동 방법 리스트를 조회하는 API")
  @GetMapping("/generate/guides")
  public ResponseEntity<CommonResponse<GetGeneratedGuidesResponse>> getGeneratedGuides(
      @AuthenticationPrincipal CustomUser user,
      @RequestParam("page") @Min(1) Integer page
  ) {
    Page<Generation> guides = generationService.getGenerationsHasNoMaterialByMemberIdAndPage(
        user.getId(), page - 1, Policy.STORAGE_GUIDE_PAGE_SIZE);
    PageInfo pageInfo = new PageInfo(page, guides.getTotalPages());

    GetGeneratedGuidesResponse response =
        new GetGeneratedGuidesResponse(guides.getContent(), pageInfo);
    return ResponseEntity.ok(new CommonResponse<>(response));
  }

  @Operation(summary = "생성한 활동 리스트 조회", description = "사용자가 생성한 활동 리스트를 조회하는 API")
  @GetMapping("/generate/activities")
  public ResponseEntity<CommonResponse<GetGeneratedActivitiesResponse>> getGeneratedActivities(
      @AuthenticationPrincipal CustomUser user,
      @RequestParam("page") @Min(1) Integer page
  ) {
    Page<Generation> activities = generationService.getGenerationsHasMaterialByMemberIdAndPage(
        user.getId(), page - 1, Policy.STORAGE_ACTIVITY_PAGE_SIZE);
    PageInfo pageInfo = new PageInfo(page, activities.getTotalPages());

    List<GenerationBundle> generationBundles =
        generationService.convertToGenerationBundle(activities.getContent());

    GetGeneratedActivitiesResponse response =
        new GetGeneratedActivitiesResponse(generationBundles, pageInfo);
    return ResponseEntity.ok(new CommonResponse<>(response));
  }

  @AuthorizeOwner(Generation.class)
  @Operation(summary = "생성한 활동 방법 상세", description = "사용자가 생성한 활동 방법 상세를 조회하는 API")
  @GetMapping("/generate/guides/{generationId}")
  public ResponseEntity<CommonResponse<GetGeneratedGuideDetailResponse>> getGeneratedGuideDetail(
      @PathVariable("generationId") Long generationId
  ) {
    Generation generation = generationService.getGenerationWithActivityOrThrow(generationId);

    GetGeneratedGuideDetailResponse response = new GetGeneratedGuideDetailResponse(generation);
    return ResponseEntity.ok(new CommonResponse<>(response));
  }

  @AuthorizeOwner(Generation.class)
  @Operation(summary = "생성한 활동 상세", description = "사용자가 생성한 활동 상세를 조회하는 API")
  @GetMapping("/generate/activities/{generationId}")
  public ResponseEntity<CommonResponse<GetGeneratedActivityDetailResponse>> getGeneratedActivityDetail(
      @PathVariable("generationId") Long generationId
  ) {
    Generation generation = generationService.getGenerationWithActivityOrThrow(generationId);
    List<Material> materials = materialService.getMaterialsWithThumbnailByActivityId(
        generation.getActivity().getId());
    boolean isMarketUploaded = marketItemService.existsByActivityId(
        generation.getActivity().getId());
    GetGeneratedActivityDetailResponse response = new GetGeneratedActivityDetailResponse(
        generation, materials, isMarketUploaded);
    return ResponseEntity.ok(new CommonResponse<>(response));
  }

  @AuthorizeOwner(Generation.class)
  @Operation(summary = "생성한 활동 방법 삭제", description = "사용자가 생성한 활동 방법을 삭제하는 API")
  @DeleteMapping("/generate/guides/{generationId}")
  public ResponseEntity<EmptyDataResponse> deleteGeneratedGuide(
      @PathVariable("generationId") Long generationId
  ) {
    generationService.deleteGeneration(generationId);
    return ResponseEntity.ok(new EmptyDataResponse(GUIDE_GENERATION_DELETED));
  }

  @AuthorizeOwner(Generation.class)
  @Operation(summary = "생성한 활동 삭제", description = "사용자가 생성한 활동을 삭제하는 API")
  @DeleteMapping("/generate/activities/{generationId}")
  public ResponseEntity<EmptyDataResponse> deleteGeneratedActivity(
      @PathVariable("generationId") Long generationId
  ) {
    generationService.deleteGeneration(generationId);
    return ResponseEntity.ok(new EmptyDataResponse(ACTIVITY_GENERATION_DELETED));
  }

  @Operation(summary = "구매한 활동 리스트 조회", description = "사용자가 구매한 활동 리스트를 조회하는 API")
  @GetMapping("/generate/purchases")
  public ResponseEntity<CommonResponse<GetPurchasedActivitiesResponse>> getPurchasedActivities(
      @AuthenticationPrincipal CustomUser user,
      @RequestParam("page") @Min(1) Integer page
  ) {
    Sort sort = Sort.by(Direction.DESC, "createdAt");
    Page<Deal> deals = dealService.getDealsByMemberId(user.getId(), page - 1,
        Policy.STORAGE_ACTIVITY_PAGE_SIZE, sort);
    PageInfo pageInfo = new PageInfo(page, deals.getTotalPages());

    List<DealBundle> generationWithThumbnails = dealService.convertToDealBundle(deals.getContent());
    GetPurchasedActivitiesResponse getPurchasedActivitiesResponse = new GetPurchasedActivitiesResponse(
        generationWithThumbnails, pageInfo);
    return ResponseEntity.ok(new CommonResponse<>(getPurchasedActivitiesResponse));
  }

  @AuthorizeOwner(Deal.class)
  @Operation(summary = "구매한 활동 상세", description = "사용자가 구매한 활동 상세를 조회하는 API")
  @GetMapping("/generate/purchases/{dealId}")
  public ResponseEntity<CommonResponse<GetPurchasedActivityDetailResponse>> getPurchasedActivityDetail(
      @PathVariable("dealId") Long dealId
  ) {
    Deal deal = dealService.getDealWithActivityOrThrow(dealId);
    List<Material> materials = materialService.getMaterialsWithThumbnailByActivityId(
        deal.getActivity().getId());
    GetPurchasedActivityDetailResponse response = new GetPurchasedActivityDetailResponse(deal,
        materials);
    return ResponseEntity.ok(new CommonResponse<>(response));
  }

  @AuthorizeOwner(Generation.class)
  @Operation(summary = "생성한 활동 자료 다운로드", description = "사용자가 생성한 활동의 자료를 다운로드하는 API")
  @GetMapping("/generate/activities/{generationId}/download")
  public ResponseEntity<CommonResponse<DownloadMaterialResponse>> downloadGeneratedActivityMaterial(
      @PathVariable("generationId") Long generationId
  ) {
    Generation generation = generationService.getGenerationWithActivityOrThrow(generationId);
    List<Material> materials = materialService.getMaterialsByActivityId(
        generation.getActivity().getId());
    DownloadMaterialResponse response = new DownloadMaterialResponse(materials);
    return ResponseEntity.ok(new CommonResponse<>(response));
  }

  @AuthorizeOwner(Deal.class)
  @Operation(summary = "구매 자료 다운로드", description = "사용자가 구매한 활동의 자료를 다운로드하는 API")
  @GetMapping("/generate/purchases/{dealId}/download")
  public ResponseEntity<CommonResponse<DownloadMaterialResponse>> downloadPurchasedActivityMaterial(
      @PathVariable("dealId") Long dealId
  ) {
    Deal deal = dealService.getDealWithActivityOrThrow(dealId);
    List<Material> materials = materialService.getMaterialsByActivityId(
        deal.getActivity().getId());
    DownloadMaterialResponse response = new DownloadMaterialResponse(materials);
    return ResponseEntity.ok(new CommonResponse<>(response));
  }
}
