package com.example.gemm_server.common.enums;

import lombok.Getter;

@Getter
public enum PeriodType {
  WEEK("최근 7일", 7),
  MONTH("최근 30일", 30),
  ALL("전체", null);

  private final String description;
  private final Integer daysAgo;

  PeriodType(String description, Integer daysAgo) {
    this.description = description;
    this.daysAgo = daysAgo;
  }
}
