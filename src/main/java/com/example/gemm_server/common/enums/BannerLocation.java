package com.example.gemm_server.common.enums;

import lombok.Getter;

@Getter
public enum BannerLocation {
  MAIN("메인"),
  SUB("서브");

  private final String description;

  BannerLocation(String description) {
    this.description = description;
  }
}
