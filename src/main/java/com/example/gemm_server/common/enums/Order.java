package com.example.gemm_server.common.enums;

import lombok.Getter;

@Getter
public enum Order {
  RECOMMENDED("추천순"),
  NEWEST_FIRST("최신순"),
  RATING_HIGH_TO_LOW("평점 높은 순"),
  RATING_LOW_TO_HIGH("퍙점 낮은 순"),
  SCRAP("스크랩순"),
  PRICE_HIGH_TO_LOW("가격 높은 순"),
  PRICE_LOW_TO_HIGH("가격 낮은 순"),
  TITLE("제목순");

  private final String description;

  Order(String description) {
    this.description = description;
  }
}
