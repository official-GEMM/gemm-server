package com.example.gemm_server.dto.admin;

import lombok.Getter;

@Getter
public class AnalyticsAverage {

  private Double layoutCompletenessAvg;
  private Double readabilityAvg;
  private Double generationTimeAvg;

  public AnalyticsAverage(Double layoutCompletenessAvg, Double readabilityAvg,
      Double generationTimeAvg) {
    this.layoutCompletenessAvg = layoutCompletenessAvg;
    this.readabilityAvg = readabilityAvg;
    this.generationTimeAvg = generationTimeAvg;
  }
}