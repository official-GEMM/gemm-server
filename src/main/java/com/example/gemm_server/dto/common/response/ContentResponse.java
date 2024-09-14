package com.example.gemm_server.dto.common.response;

import com.example.gemm_server.common.enums.Format;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
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

  public static ContentResponse[] of(String content) {
    return Arrays.stream(content.split("\n")).map(ContentResponse::new)
        .toArray(ContentResponse[]::new);
  }

  public static String getFormatString(ContentResponse content) {
    return content.format.getPrefix() + content.content.strip() + "\n";
  }

  public static String getCompleteFormatString(List<ContentResponse> contents) {
    return contents.stream().map(ContentResponse::getFormatString).collect(Collectors.joining());
  }
}
