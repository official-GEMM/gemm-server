package com.example.gemm_server.service;

import com.example.gemm_server.common.enums.Category;
import com.example.gemm_server.domain.entity.Analytics;
import com.example.gemm_server.domain.repository.AnalyticsRepository;
import com.example.gemm_server.dto.generator.response.LlmPptResponse;
import jakarta.transaction.Transactional;
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
}
