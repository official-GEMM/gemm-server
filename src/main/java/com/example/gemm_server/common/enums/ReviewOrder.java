package com.example.gemm_server.common.enums;

import lombok.Getter;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;

@Getter
public enum ReviewOrder {

  RATING_HIGH_TO_LOW("평점 높은 순", new String[]{"score"}, Direction.DESC),
  RATING_LOW_TO_HIGH("평점 낮은 순", new String[]{"score"}, Direction.ASC);

  private final String description;
  private final String[] properties;
  private final Direction direction;

  public Sort getSort() {
    return Sort.by(direction, properties);
  }

  ReviewOrder(String description, String[] properties, Direction direction) {
    this.description = description;
    this.properties = properties;
    this.direction = direction;
  }
}
