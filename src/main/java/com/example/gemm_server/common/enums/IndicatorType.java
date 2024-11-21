package com.example.gemm_server.common.enums;

import lombok.Getter;

@Getter
public enum IndicatorType {
  LAYOUT_COMPLETENESS("레이아웃 완성도") {
    public boolean hasDeveloped(float lastValue, float nextValue) {
      return lastValue < nextValue;
    }
  },
  READABILITY("가독성") {
    public boolean hasDeveloped(float lastValue, float nextValue) {
      return lastValue < nextValue;
    }
  },
  GENERATION_TIME("생성에 소요된 시간") {
    public boolean hasDeveloped(float lastValue, float nextValue) {
      return lastValue > nextValue;
    }
  };

  public abstract boolean hasDeveloped(float lastValue, float nextValue);

  private final String description;

  IndicatorType(String description) {
    this.description = description;
  }
}
