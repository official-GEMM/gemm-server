package com.example.gemm_server.common.enums;

import lombok.Getter;

@Getter
public enum MaterialType {
  PPT("피피티"),
  ACTIVITY_SHEET("활동지"),
  CUTOUT("컷도안");

  private final String description;

  MaterialType(String description) {
    this.description = description;
  }
}