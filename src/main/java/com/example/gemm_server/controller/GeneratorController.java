package com.example.gemm_server.controller;

import com.example.gemm_server.common.annotation.auth.BearerAuth;
import com.example.gemm_server.dto.CommonResponse;
import com.example.gemm_server.dto.generator.request.GenerateActivityGuideRequest;
import com.example.gemm_server.dto.generator.request.SaveActivityGuideRequest;
import com.example.gemm_server.dto.generator.response.ActivityGuideResponse;
import com.example.gemm_server.dto.generator.response.SavedActivityResponse;
import com.example.gemm_server.security.jwt.CustomUser;
import com.example.gemm_server.service.ActivityService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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
    public ResponseEntity<CommonResponse<ActivityGuideResponse>> generateActivityGuide(
            @Valid @RequestBody GenerateActivityGuideRequest generateActivityGuideRequest
    ) {
        ActivityGuideResponse activityGuideResponse = activityService.generateActivityGuide(generateActivityGuideRequest);
        return ResponseEntity.ok(new CommonResponse<>(activityGuideResponse));
    }

    @Operation(summary = "활동 방법 저장", description = "활동 방법을 내 저장소에 저장하는 API")
    @PostMapping("/guide/result")
    public ResponseEntity<CommonResponse<SavedActivityResponse>> saveActivityGuide(
            @Valid @RequestBody SaveActivityGuideRequest saveActivityGuideRequest
//            @AuthenticationPrincipal CustomUser user
            ) {
        SavedActivityResponse savedActivityResponse = activityService.saveActivityGuide(saveActivityGuideRequest, 1L);
        return ResponseEntity.ok(new CommonResponse<>(savedActivityResponse));
    }
//
//    @Operation(summary = "활동 방법 수정", description = "활동 방법을 수정하는 API")
//    @PutMapping("/guide/result")
//    public ResponseEntity<CommonResponse<?>> generateActivityGuide() {
//        return null;
//    }
//
//    @Operation(summary = "활동 방법 자료 생성 연동", description = "생성된 활동 방법을 자료 생성에 연동하는 API")
//    @PostMapping("/guide/guide/sync")
//    public ResponseEntity<CommonResponse<?>> generateActivityGuide() {
//        return null;
//    }
}
