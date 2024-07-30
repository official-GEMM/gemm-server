package com.example.gemm_server.common.enums;

import lombok.Getter;

public enum EventType {
  PURCHASE("구매"),
  REVIEW("리뷰"),
  REFERRAL("추천인");

  @Getter
  private final String description;

  EventType(String description) {
    this.description = description;
  }
}

