package com.example.gemm_server.controller;

import com.example.gemm_server.dto.CommonResponse;
import com.example.gemm_server.dto.generator.request.GenerateGuideRequest;
import com.example.gemm_server.dto.generator.request.SaveGuideRequest;
import com.example.gemm_server.dto.generator.request.UpdateGuideRequest;
import com.example.gemm_server.dto.generator.response.GeneratedGuideResponse;
import com.example.gemm_server.dto.generator.response.SavedGenerationResponse;
import com.example.gemm_server.dto.generator.response.UpdatedGuideResponse;
import com.example.gemm_server.service.ActivityService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    ) {
        GeneratedGuideResponse generatedGuideResponse = activityService.generateActivityGuide(generateGuideRequest);
        return ResponseEntity.ok(new CommonResponse<>(generatedGuideResponse));
    }

    @Operation(summary = "활동 방법 저장", description = "활동 방법을 내 저장소에 저장하는 API")
    @PostMapping("/guide/result")
    public ResponseEntity<CommonResponse<SavedGenerationResponse>> saveActivityGuide(
            @Valid @RequestBody SaveGuideRequest saveGuideRequest
//            @AuthenticationPrincipal CustomUser user
    ) {
        SavedGenerationResponse savedGenerationResponse = activityService.saveActivityGuide(saveGuideRequest, 1L);
        return ResponseEntity.ok(new CommonResponse<>(savedGenerationResponse));
    }

    @Operation(summary = "활동 방법 수정", description = "활동 방법을 수정하는 API")
    @PutMapping("/guide/result")
    public ResponseEntity<CommonResponse<UpdatedGuideResponse>> updateActivityGuide(
            @Valid @RequestBody UpdateGuideRequest updateGuideRequest
    ) {
        UpdatedGuideResponse updatedGuideResponse = activityService.updateActivityGuide(updateGuideRequest);
        return ResponseEntity.ok(new CommonResponse<>(updatedGuideResponse));
    }
}
