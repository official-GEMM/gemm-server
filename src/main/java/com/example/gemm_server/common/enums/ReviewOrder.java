package com.example.gemm_server.common.enums;

import lombok.Getter;

@Getter
public enum ReviewOrder {

  RATING_HIGH_TO_LOW("평점 높은 순"),
  RATING_LOW_TO_HIGH("평점 낮은 순");

  private final String description;

  ReviewOrder(String description) {
    this.description = description;
  }
}
