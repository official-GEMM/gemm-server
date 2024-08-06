package com.example.gemm_server.controller;

import com.example.gemm_server.common.enums.Category;
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
import com.example.gemm_server.dto.generator.response.ActivitySheetPathResponse;
import com.example.gemm_server.dto.generator.response.CommentedActivitySheetResponse;
import com.example.gemm_server.dto.generator.response.CommentedCutoutResponse;
import com.example.gemm_server.dto.generator.response.CommentedPptResponse;
import com.example.gemm_server.dto.generator.response.CutoutPathResponse;
import com.example.gemm_server.dto.generator.response.GeneratedGuideResponse;
import com.example.gemm_server.dto.generator.response.GeneratedMaterialsResponse;
import com.example.gemm_server.dto.generator.response.LinkedMaterialGuideResponse;
import com.example.gemm_server.dto.generator.response.PptPathResponse;
import com.example.gemm_server.dto.generator.response.SavedGuideResponse;
import com.example.gemm_server.dto.generator.response.SavedMaterialResponse;
import com.example.gemm_server.dto.generator.response.UpdatedActivitySheetResponse;
import com.example.gemm_server.dto.generator.response.UpdatedCutoutResponse;
import com.example.gemm_server.dto.generator.response.UpdatedGuideResponse;
import com.example.gemm_server.dto.generator.response.UpdatedPptResponse;
import com.example.gemm_server.service.ActivityService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.net.MalformedURLException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

//@BearerAuth
@RequiredArgsConstructor
@RestController
@RequestMapping("/generate")
@Tag(name = "AI Generator", description = "LLM 서버를 이용하여 결과물을 생성하고 반환하는 API")
public class GeneratorController {

  private final ActivityService activityService;

  @Operation(summary = "활동 방법 생성", description = "활동 방법을 생성하는 API")
  @PostMapping("/guide")
  public ResponseEntity<CommonResponse<GeneratedGuideResponse>> generateActivityGuide(
      @Valid @RequestBody GenerateGuideRequest generateGuideRequest
      //            @AuthenticationPrincipal CustomUser user
  ) {
    GeneratedGuideResponse generatedGuideResponse = activityService.generateGuide(
        generateGuideRequest, 1L);
    return ResponseEntity.ok(new CommonResponse<>(generatedGuideResponse));
  }

  @Operation(summary = "활동 방법 저장", description = "활동 방법을 내 저장소에 저장하는 API")
  @PostMapping("/guide/result")
  public ResponseEntity<CommonResponse<SavedGuideResponse>> saveActivityGuide(
      @Valid @RequestBody SaveGuideRequest saveGuideRequest
//            @AuthenticationPrincipal CustomUser user
  ) {
    SavedGuideResponse savedGuideResponse = activityService.saveGuide(saveGuideRequest, 1L);
    return ResponseEntity.ok(new CommonResponse<>(savedGuideResponse));
  }

  @Operation(summary = "활동 방법 수정", description = "활동 방법을 수정하는 API")
  @PutMapping("/guide/result")
  public ResponseEntity<CommonResponse<UpdatedGuideResponse>> updateActivityGuide(
      @Valid @RequestBody UpdateGuideRequest updateGuideRequest
      //            @AuthenticationPrincipal CustomUser user
  ) {
    UpdatedGuideResponse updatedGuideResponse = activityService.updateGuide(updateGuideRequest, 1L);
    return ResponseEntity.ok(new CommonResponse<>(updatedGuideResponse));
  }

  @Operation(summary = "활동 방법 자료 생성 연동", description = "활동 방법을 자료 생성에 연동하는 API")
  @PostMapping("/guide/sync")
  public ResponseEntity<CommonResponse<LinkedMaterialGuideResponse>> linkGuideToMaterial(
      @Valid @RequestBody LinkMaterialGuideRequest linkedMaterialGuideResponse
      //            @AuthenticationPrincipal CustomUser user
  ) {
    String[] test = {"1", "2", "3"};
    return ResponseEntity.ok(new CommonResponse<>(new LinkedMaterialGuideResponse(
        "", (short) 2, Category.ART_AREA, "", test, "", ""
    )));
  }

  @Operation(summary = "활동 자료 생성", description = "활동 자료를 생성하는 API")
  @PostMapping("/materials")
  public ResponseEntity<CommonResponse<GeneratedMaterialsResponse>> generateMaterial(
      @Valid @RequestBody GenerateMaterialRequest generateMaterialRequest
      //            @AuthenticationPrincipal CustomUser user
  ) throws MalformedURLException {
    String testSource = new String("/test");
    String[] testSources = new String[]{new String("test1"), new String("test2")};
    return ResponseEntity.ok(new CommonResponse<>(new GeneratedMaterialsResponse(
        new PptPathResponse(testSources, testSource),
        new ActivitySheetPathResponse(testSource, testSource),
        new CutoutPathResponse(testSource, testSource),
        0
    )));
  }

  @Operation(summary = "활동 자료 저장", description = "활동 자료를 저장하는 API")
  @PostMapping("/materials/result")
  public ResponseEntity<CommonResponse<SavedMaterialResponse>> saveMaterial(
      @Valid @RequestBody SaveMaterialRequest saveMaterialRequest
      //            @AuthenticationPrincipal CustomUser user
  ) {
    return ResponseEntity.ok(new CommonResponse<>(new SavedMaterialResponse(
        0L
    )));
  }

  @Operation(summary = "PPT 자료 수정", description = "PPT를 수정하는 API")
  @PutMapping("/materials/result/ppt")
  public ResponseEntity<CommonResponse<UpdatedPptResponse>> updatePpt(
      @Valid @RequestBody UpdatePptRequest updatePptRequest
      //            @AuthenticationPrincipal CustomUser user
  ) throws MalformedURLException {
    String testSource = new String("/test");
    String[] testSources = new String[]{new String("test1"), new String("test2")};
    return ResponseEntity.ok(new CommonResponse<>(new UpdatedPptResponse(
        new CommentedPptResponse(testSources, testSource),
        0
    )));
  }

  @Operation(summary = "활동지 자료 수정", description = "활동지를 수정하는 API")
  @PutMapping("/materials/result/activitySheet")
  public ResponseEntity<CommonResponse<UpdatedActivitySheetResponse>> updateActivitySheet(
      @Valid @RequestBody UpdateActivitySheetRequest updateActivitySheetRequest
      //            @AuthenticationPrincipal CustomUser user
  ) throws MalformedURLException {
    String testSource = new String("/test");
    return ResponseEntity.ok(new CommonResponse<>(new UpdatedActivitySheetResponse(
        new CommentedActivitySheetResponse(testSource, testSource),
        0
    )));
  }

  @Operation(summary = "컷/도안 자료 수정", description = "컷/도안을 수정하는 API")
  @PutMapping("/materials/result/cutout")
  public ResponseEntity<CommonResponse<UpdatedCutoutResponse>> updateCutout(
      @Valid @RequestBody UpdateCutoutRequest updateCutoutRequest
      //            @AuthenticationPrincipal CustomUser user
  ) throws MalformedURLException {
    String testSource = new String("/test");
    return ResponseEntity.ok(new CommonResponse<>(new UpdatedCutoutResponse(
        new CommentedCutoutResponse(testSource, testSource),
        0
    )));
  }
}
