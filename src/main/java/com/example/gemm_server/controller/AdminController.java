package com.example.gemm_server.controller;

import com.example.gemm_server.common.annotation.auth.Admin;
import com.example.gemm_server.common.annotation.auth.BearerAuth;
import com.example.gemm_server.common.enums.PeriodType;
import com.example.gemm_server.dto.CommonResponse;
import com.example.gemm_server.dto.admin.AnalyticsAverage;
import com.example.gemm_server.dto.admin.response.GetAnalyticsStatusResponse;
import com.example.gemm_server.service.AnalyticsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@BearerAuth
@Admin
@RequiredArgsConstructor
@RestController()
@RequestMapping("/admin")
@Tag(name = "Admin", description = "관리자 API")
public class AdminController {

  private final AnalyticsService analyticsService;

  @Operation(summary = "분석 지표 조회", description = "분석 지표를 조회하는 API")
  @GetMapping("/analytics/stat")
  public ResponseEntity<CommonResponse<GetAnalyticsStatusResponse>> getAnalyticsStatus(
      @RequestParam("periodType") PeriodType periodType
  ) {
    AnalyticsAverage lastAnalyticsAverage = analyticsService.getLastAverage(periodType);
    AnalyticsAverage recentAnalyticsAverage = analyticsService.getRecentAverage(periodType);
    GetAnalyticsStatusResponse response = new GetAnalyticsStatusResponse(lastAnalyticsAverage,
        recentAnalyticsAverage);
    return ResponseEntity.ok(new CommonResponse<>(response));
  }
}
