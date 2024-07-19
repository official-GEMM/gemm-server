package com.example.gemm_server.common;

import lombok.Getter;

@Getter
public enum Role {
  USER("일반 사용자"),
  ADMIN("관리자");

  private final String name;

  Role(String name) {
    this.name = name;
  }
}
