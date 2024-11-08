package com.example.gemm_server.controller;

import com.example.gemm_server.common.annotation.auth.BearerAuth;
import com.example.gemm_server.dto.CommonResponse;
import com.example.gemm_server.dto.generator.request.GenerateGuideRequest;
import com.example.gemm_server.dto.generator.request.GenerateMaterialRequest;
import com.example.gemm_server.dto.generator.request.LinkMaterialGuideRequest;
import com.example.gemm_server.dto.generator.request.SaveGuideRequest;
import com.example.gemm_server.dto.generator.request.SaveMaterialRequest;
import com.example.gemm_server.dto.generator.request.UpdateActivitySheetRequest;
import com.example.gemm_server.dto.generator.request.UpdateCutoutRequest;
import com.example.gemm_server.dto.generator.request.UpdateGuideRequest;
import com.example.gemm_server.dto.generator.request.UpdatePptRequest;
import com.example.gemm_server.dto.generator.response.ActivitySheetTemplatesResponse;
import com.example.gemm_server.dto.generator.response.GenerateGuideResponse;
import com.example.gemm_server.dto.generator.response.GeneratedMaterialsResponse;
import com.example.gemm_server.dto.generator.response.LinkedMaterialGuideResponse;
import com.example.gemm_server.dto.generator.response.PptTemplatesResponse;
import com.example.gemm_server.dto.generator.response.SavedGuideResponse;
import com.example.gemm_server.dto.generator.response.SavedMaterialResponse;
import com.example.gemm_server.dto.generator.response.UpdatedActivitySheetResponse;
import com.example.gemm_server.dto.generator.response.UpdatedCutoutResponse;
import com.example.gemm_server.dto.generator.response.UpdatedGuideResponse;
import com.example.gemm_server.dto.generator.response.UpdatedPptResponse;
import com.example.gemm_server.security.jwt.CustomUser;
import com.example.gemm_server.service.ActivityService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@BearerAuth
@RequiredArgsConstructor
@RestController
@RequestMapping("/generate")
@Tag(name = "AI Generator", description = "LLM 서버를 이용하여 결과물을 생성하고 반환하는 API")
public class GeneratorController {

  private final ActivityService activityService;

  @Operation(summary = "활동 방법 생성", description = "활동 방법을 생성하는 API")
  @PostMapping("/guide")
  public ResponseEntity<CommonResponse<GenerateGuideResponse>> generateActivityGuide(
      @Valid @RequestBody GenerateGuideRequest generateGuideRequest,
      @AuthenticationPrincipal CustomUser user
  ) {
    GenerateGuideResponse generateGuideResponse = activityService.generateGuide(
        generateGuideRequest, user.getId());
    return ResponseEntity.ok(new CommonResponse<>(generateGuideResponse));
  }

  @Operation(summary = "활동 방법 저장", description = "활동 방법을 내 저장소에 저장하는 API")
  @PostMapping("/guide/result")
  public ResponseEntity<CommonResponse<SavedGuideResponse>> saveActivityGuide(
      @Valid @RequestBody SaveGuideRequest saveGuideRequest,
      @AuthenticationPrincipal CustomUser user
  ) {
    SavedGuideResponse savedGuideResponse = activityService.saveGuide(saveGuideRequest,
        user.getId());
    return ResponseEntity.ok(new CommonResponse<>(savedGuideResponse));
  }

  @Operation(summary = "활동 방법 수정", description = "활동 방법을 수정하는 API")
  @PutMapping("/guide/result")
  public ResponseEntity<CommonResponse<UpdatedGuideResponse>> updateActivityGuide(
      @Valid @RequestBody UpdateGuideRequest updateGuideRequest,
      @AuthenticationPrincipal CustomUser user
  ) {
    UpdatedGuideResponse updatedGuideResponse = activityService.updateGuide(updateGuideRequest,
        user.getId());
    return ResponseEntity.ok(new CommonResponse<>(updatedGuideResponse));
  }

  @Operation(summary = "활동 방법 자료 생성 연동", description = "활동 방법을 자료 생성에 연동하는 API")
  @PostMapping("/guide/sync")
  public ResponseEntity<CommonResponse<LinkedMaterialGuideResponse>> linkGuideToMaterial(
      @Valid @RequestBody LinkMaterialGuideRequest linkMaterialGuideRequest
  ) {
    LinkedMaterialGuideResponse linkedMaterialGuideResponse = activityService.linkGuideToMaterial(
        linkMaterialGuideRequest);
    return ResponseEntity.ok(new CommonResponse<>(linkedMaterialGuideResponse));
  }

  @Operation(summary = "활동 자료 생성", description = "활동 자료를 생성하는 API")
  @PostMapping("/materials")
  public ResponseEntity<CommonResponse<GeneratedMaterialsResponse>> generateMaterial(
      @Valid @RequestBody GenerateMaterialRequest generateMaterialRequest,
      @AuthenticationPrincipal CustomUser user
  ) {
    GeneratedMaterialsResponse generatedMaterialsResponse = activityService.generateMaterials(
        generateMaterialRequest, user.getId());
    return ResponseEntity.ok(new CommonResponse<>(generatedMaterialsResponse));
  }

  @Operation(summary = "활동 자료 저장", description = "활동 자료를 저장하는 API")
  @PostMapping("/materials/result")
  public ResponseEntity<CommonResponse<SavedMaterialResponse>> saveMaterial(
      @Valid @RequestBody SaveMaterialRequest saveMaterialRequest,
      @AuthenticationPrincipal CustomUser user
  ) {
    SavedMaterialResponse savedMaterialResponse = activityService.saveMaterials(saveMaterialRequest,
        user.getId());
    return ResponseEntity.ok(new CommonResponse<>(savedMaterialResponse));
  }

  @Operation(summary = "PPT 자료 수정", description = "PPT를 수정하는 API")
  @PutMapping("/materials/result/ppt")
  public ResponseEntity<CommonResponse<UpdatedPptResponse>> updatePpt(
      @Valid @RequestBody UpdatePptRequest updatePptRequest,
      @AuthenticationPrincipal CustomUser user
  ) {
    UpdatedPptResponse updatedPptResponse = activityService.updatePpt(updatePptRequest,
        user.getId());
    return ResponseEntity.ok(new CommonResponse<>(updatedPptResponse));
  }

  @Operation(summary = "활동지 자료 수정", description = "활동지를 수정하는 API")
  @PutMapping("/materials/result/activitySheet")
  public ResponseEntity<CommonResponse<UpdatedActivitySheetResponse>> updateActivitySheet(
      @Valid @RequestBody UpdateActivitySheetRequest updateActivitySheetRequest,
      @AuthenticationPrincipal CustomUser user
  ) {
    UpdatedActivitySheetResponse updatedActivitySheetResponse = activityService.updateActivitySheet(
        updateActivitySheetRequest, user.getId());
    return ResponseEntity.ok(new CommonResponse<>(updatedActivitySheetResponse));
  }

  @Operation(summary = "컷/도안 자료 수정", description = "컷/도안을 수정하는 API")
  @PutMapping("/materials/result/cutout")
  public ResponseEntity<CommonResponse<UpdatedCutoutResponse>> updateCutout(
      @Valid @RequestBody UpdateCutoutRequest updateCutoutRequest,
      @AuthenticationPrincipal CustomUser user
  ) {
    UpdatedCutoutResponse updatedCutoutResponse = activityService.updateCutout(updateCutoutRequest,
        user.getId());
    return ResponseEntity.ok(new CommonResponse<>(updatedCutoutResponse));
  }

  @Operation(summary = "PPT 템플릿 조회", description = "s3로부터 PPT 템플릿 대표 이미지와 번호를 불러오는 API")
  @GetMapping("/materials/template/ppt")
  public ResponseEntity<CommonResponse<PptTemplatesResponse>> getPptTemplates() {
    PptTemplatesResponse pptTemplates = activityService.getPptTemplates();
    return ResponseEntity.ok(new CommonResponse<>(pptTemplates));
  }

  @Operation(summary = "활동지 템플릿 조회", description = "s3로부터 활동지 템플릿 대표 이미지와 번호를 불러오는 API")
  @GetMapping("/materials/template/activitySheet")
  public ResponseEntity<CommonResponse<ActivitySheetTemplatesResponse>> getActivitySheetTemplates() {
    ActivitySheetTemplatesResponse activitySheetTemplatesResponse = activityService.getActivitySheetTemplates();
    return ResponseEntity.ok(new CommonResponse<>(activitySheetTemplatesResponse));
  }
}
