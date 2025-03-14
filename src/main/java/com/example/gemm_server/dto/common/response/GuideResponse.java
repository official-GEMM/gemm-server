package com.example.gemm_server.dto.common.response;

import com.example.gemm_server.common.enums.Category;
import com.example.gemm_server.domain.entity.Activity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
@Schema(description = "활동 방법 응답", requiredProperties = {"title", "age", "category", "contents"})
public class GuideResponse {

  @Schema(description = "제목")
  private String title;

  @Schema(description = "타겟 연령")
  private short age;

  @Schema(description = "영역 및 활동")
  private Category category;

  @Schema(description = "내용과 형식")
  private ContentResponse[] contents;

  public GuideResponse(Activity activity) {
    this.title = activity.getTitle();
    this.age = activity.getAge();
    this.category = activity.getCategory();
    this.contents = ContentResponse.of(activity.getContent());
  }
}
