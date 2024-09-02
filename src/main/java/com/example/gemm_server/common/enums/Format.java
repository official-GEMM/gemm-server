package com.example.gemm_server.common.enums;

import lombok.Getter;

@Getter
public enum Format {
  TITLE("제목", "# "),
  ELEMENT("요소", "- "),
  DESCRIPTION("설명", "");

  private final String description;
  private final String prefix;

  Format(String description, String prefix) {
    this.description = description;
    this.prefix = prefix;
  }

  public static Format getFormat(String content) {
    if (content.startsWith(TITLE.prefix)) {
      return TITLE;
    }
    if (content.startsWith(ELEMENT.prefix)) {
      return ELEMENT;
    }
    return DESCRIPTION;
  }

  public String removePrefix(String content) {
    return content.substring(this.prefix.length());
  }
}
