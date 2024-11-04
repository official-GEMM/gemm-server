package com.example.gemm_server.common.enums;

import lombok.Getter;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;

@Getter
public enum Order {
  /**
   * 추천 공식: W1(0.5) * R(리뷰 평균) + W2(0.3) * max(0,1−0.05×(업로드 날짜 - 오늘 날짜)) + W3(0.2) * log(1+자료 구매수)
   */
  RECOMMENDED("추천순", new String[]{"scrapCount"}, Direction.DESC), // TODO: 추천 공식 정리
  NEWEST_FIRST("최신순", new String[]{"createdAt"}, Direction.DESC),
  RATING("평점순", new String[]{"averageScore"}, Direction.DESC),
  SCRAP("스크랩순", new String[]{"scrapCount"}, Direction.DESC),
  PRICE_HIGH_TO_LOW("가격 높은 순", new String[]{"price"}, Direction.DESC),
  PRICE_LOW_TO_HIGH("가격 낮은 순", new String[]{"price"}, Direction.ASC),
  TITLE("제목순", new String[]{"title"}, Direction.ASC);

  private final String description;
  private final String[] properties;
  private final Direction direction;

  public Sort sortBy() {
    return Sort.by(direction, properties);
  }

  Order(String description, String[] properties, Direction direction) {
    this.description = description;
    this.properties = properties;
    this.direction = direction;
  }
}
