package com.example.gemm_server.dto.common.response;

import com.example.gemm_server.common.enums.Category;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
@Schema(description = "활동 상세 응답", requiredProperties = {"title", "materials", "age", "category",
    "content"})
public class ActivityDetailResponse {

  @Schema(description = "제목")
  private String title;

  @Schema(description = "자료 리스트")
  private MaterialResponse[] materials;

  @Schema(description = "타겟 연령")
  private int age;

  @Schema(description = "영역 및 활동")
  private Category category;

  @Schema(description = "내용")
  private String content;

  public ActivityDetailResponse() {
  }

}
