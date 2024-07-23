package com.example.gemm_server.common.enums;

import lombok.Getter;

@Getter
public enum Provider {
  KAKAO("kakao", "id"),
  NAVER("naver", "id"),
  GOOGLE("google", "sub");

  private final String name;
  private final String attributeNameOfId;

  Provider(String name, String attributeNameOfId) {
    this.name = name;
    this.attributeNameOfId = attributeNameOfId;
  }

  public boolean isEqual(String name) {
    return this.name.equals(name);
  }
}
