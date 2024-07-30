package com.example.gemm_server.common.enums;

import lombok.Getter;

@Getter
public enum SearchType {
  TOTAL("전체"),
  TITLE("제목"),
  SELLER("판매자");

  private final String description;

  SearchType(String description) {
    this.description = description;
  }
}