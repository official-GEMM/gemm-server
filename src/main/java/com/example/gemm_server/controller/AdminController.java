package com.example.gemm_server.controller;

import static com.example.gemm_server.common.constant.Policy.ANALYTICS_PAGE_SIZE;

import com.example.gemm_server.common.annotation.auth.Admin;
import com.example.gemm_server.common.annotation.auth.BearerAuth;
import com.example.gemm_server.common.enums.PeriodType;
import com.example.gemm_server.domain.entity.Analytics;
import com.example.gemm_server.dto.CommonResponse;
import com.example.gemm_server.dto.admin.AnalyticsAverage;
import com.example.gemm_server.dto.admin.response.GetAnalyticsDetailResponse;
import com.example.gemm_server.dto.admin.response.GetAnalyticsResponse;
import com.example.gemm_server.dto.admin.response.GetAnalyticsStatusResponse;
import com.example.gemm_server.dto.common.PageInfo;
import com.example.gemm_server.service.AnalyticsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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

  @Operation(summary = "분석 자료 리스트 조회", description = "분석 자료 리스트를 조회하는 API")
  @GetMapping("/analytics/activities")
  public ResponseEntity<CommonResponse<GetAnalyticsResponse>> getAnalyticsList(
      @RequestParam("page") @Min(1) Integer page,
      @RequestParam("periodType") PeriodType periodType
  ) {
    Page<Analytics> analytics = analyticsService.getAnalyticsOrderByCreatedAt(page - 1,
        ANALYTICS_PAGE_SIZE, periodType);
    PageInfo pageInfo = new PageInfo(page, analytics.getTotalPages());
    GetAnalyticsResponse response = new GetAnalyticsResponse(analytics.getContent(), pageInfo);
    return ResponseEntity.ok(new CommonResponse<>(response));
  }

  @Operation(summary = "분석 자료 상세 조회", description = "분석 자료의 상세 정보를 조회하는 API")
  @GetMapping("/analytics/activities/{analyticsId}")
  public ResponseEntity<CommonResponse<GetAnalyticsDetailResponse>> getAnalyticsDetail(
      @PathVariable("analyticsId") Long analyticsId
  ) {
    Analytics currentAnalytics = analyticsService.findByIdOrThrow(analyticsId);
    AnalyticsAverage monthAnalyticsAverage = analyticsService.getRecentAverage(PeriodType.MONTH);
    AnalyticsAverage weekAnalyticsAverage = analyticsService.getRecentAverage(PeriodType.WEEK);
    GetAnalyticsDetailResponse response = new GetAnalyticsDetailResponse(monthAnalyticsAverage,
        weekAnalyticsAverage, currentAnalytics);
    return ResponseEntity.ok(new CommonResponse<>(response));
  }
}
