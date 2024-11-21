package com.example.gemm_server.common.enums;


import lombok.Getter;

@Getter
public enum ChangeType {
  POSITIVE("긍정"),
  NEUTRAL("-"),
  NEGATIVE("부정");

  private final String description;

  public static ChangeType getChangeType(Float lastValue, Float nextValue,
      IndicatorType indicatorType) {
    float epsilon = 0.001f;
    float diff = Math.abs(lastValue - nextValue);

    if (diff <= epsilon) {
      return NEUTRAL;
    }
    if (indicatorType.hasDeveloped(lastValue, nextValue)) {
      return POSITIVE;
    }
    return NEGATIVE;
  }

  ChangeType(String description) {
    this.description = description;
  }
}
