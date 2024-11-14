package com.example.gemm_server.service;

import static com.example.gemm_server.common.code.error.AnalyticsErrorCode.ANALYTICS_NOT_FOUND;

import com.example.gemm_server.common.enums.Category;
import com.example.gemm_server.common.enums.PeriodType;
import com.example.gemm_server.common.util.DateUtil;
import com.example.gemm_server.domain.entity.Analytics;
import com.example.gemm_server.domain.repository.AnalyticsRepository;
import com.example.gemm_server.dto.admin.AnalyticsAverage;
import com.example.gemm_server.dto.generator.response.LlmPptResponse;
import com.example.gemm_server.exception.AnalyticsException;
import jakarta.transaction.Transactional;
import java.time.LocalDate;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
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

  public Analytics findByIdOrThrow(Long analyticsId) {
    return analyticsRepository.findById(analyticsId)
        .orElseThrow(() -> new AnalyticsException(ANALYTICS_NOT_FOUND));
  }

  public Page<Analytics> getAnalyticsOrderByCreatedAt(int pageNumber, int pageSize,
      PeriodType periodType) {
    Sort sort = Sort.by(Direction.DESC, "createdAt");
    Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
    if (periodType == PeriodType.ALL) {
      return analyticsRepository.findAll(pageable);
    }
    LocalDate startDate = DateUtil.getToday().minusDays(periodType.getDaysAgo());
    LocalDate endDate = DateUtil.getToday().plusDays(1);
    return analyticsRepository.findByCreatedAtBetween(pageable, startDate.atStartOfDay(),
        endDate.atStartOfDay());
  }
}
