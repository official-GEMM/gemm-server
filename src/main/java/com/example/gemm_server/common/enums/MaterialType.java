package com.example.gemm_server.common.enums;

import java.util.List;
import lombok.Getter;

@Getter
public enum MaterialType {
  PPT("피피티", (short) 0b100, List.of(".pptx")),
  ACTIVITY_SHEET("활동지", (short) 0b010, List.of("docx", "pdf")),
  CUTOUT("컷도안", (short) 0b001, List.of("jpg", "jpeg", "png"));

  private final String description;
  private final short bitMask;
  private final List<String> extensions;

  public boolean containExtension(String extension) {
    return extensions.contains(extension);
  }

  MaterialType(String description, short bitMask, List<String> extensions) {
    this.description = description;
    this.bitMask = bitMask;
    this.extensions = extensions;
  }
}