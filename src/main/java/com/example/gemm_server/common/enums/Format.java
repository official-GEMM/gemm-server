package com.example.gemm_server.common.enums;

import lombok.Getter;

@Getter
public enum Format {
  TITLE("제목"),
  ELEMENT("요소"),
  CONTENT("설명");

  private final String description;

  Format(String description) {
    this.description = description;
  }
}