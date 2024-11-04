package com.example.gemm_server.common.enums;

import lombok.Getter;

@Getter
public enum MaterialType {
  PPT("피피티", (short) 0b100),
  ACTIVITY_SHEET("활동지", (short) 0b010),
  CUTOUT("컷도안", (short) 0b001);

  private final String description;
  private final short bitMask;

  MaterialType(String description, short bitMask) {
    this.description = description;
    this.bitMask = bitMask;
  }
}