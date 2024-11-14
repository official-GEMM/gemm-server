package com.example.gemm_server.service;

import com.example.gemm_server.common.enums.Category;
import com.example.gemm_server.common.enums.PeriodType;
import com.example.gemm_server.common.util.DateUtil;
import com.example.gemm_server.domain.entity.Analytics;
import com.example.gemm_server.domain.repository.AnalyticsRepository;
import com.example.gemm_server.dto.admin.AnalyticsAverage;
import com.example.gemm_server.dto.generator.response.LlmPptResponse;
import jakarta.transaction.Transactional;
import java.time.LocalDate;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AnalyticsService {

  private final AnalyticsRepository analyticsRepository;

  @Transactional
  public Analytics saveAnalyticsInformation(LlmPptResponse pptInfo, Category category,
      String nickname) {
    Analytics analytics = analyticsRepository.save(Analytics.builder()
        .fileName(pptInfo.fileName().substring(pptInfo.fileName().lastIndexOf('/') + 1))
        .directoryPath(pptInfo.fileName().substring(0, pptInfo.fileName().lastIndexOf('/') + 1))
        .layoutCompleteness(pptInfo.layoutCompleteness())
        .readability(pptInfo.readability())
        .generationTime(pptInfo.generationTime())
        .category(category)
        .nickname(nickname)
        .build());

    return analytics;
  }

  public AnalyticsAverage getRecentAverage(PeriodType periodType) {
    if (periodType == PeriodType.ALL) {
      return analyticsRepository.findAverageScore();
    }
    LocalDate startDate = DateUtil.getToday().minusDays(periodType.getDaysAgo());
    LocalDate endDate = DateUtil.getToday().plusDays(1);
    return analyticsRepository.findAverageScoreByCreatedAtBetween(startDate.atStartOfDay(),
        endDate.atStartOfDay());
  }

  public AnalyticsAverage getLastAverage(PeriodType periodType) {
    if (periodType == PeriodType.ALL) {
      return new AnalyticsAverage(null, null, null);
    }
    int daysAgo = periodType.getDaysAgo();
    LocalDate endDate = DateUtil.getToday().minusDays(daysAgo);
    LocalDate startDate = endDate.minusDays(daysAgo);
    return analyticsRepository.findAverageScoreByCreatedAtBetween(startDate.atStartOfDay(),
        endDate.atStartOfDay());
  }
}
