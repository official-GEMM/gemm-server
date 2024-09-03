package com.example.gemm_server.dto.common.response;

import com.example.gemm_server.common.enums.Format;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(force = true)
@Schema(description = "활동 응답", requiredProperties = {"content", "format"})
public class ContentResponse {

  @Schema(description = "내용")
  private final String content;

  @Schema(description = "썸네일 경로")
  private final Format format;

  public ContentResponse(String content) {
    this.format = Format.getFormat(content);
    this.content = this.format.removePrefix(content);
  }
}
