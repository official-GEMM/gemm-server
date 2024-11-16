package com.example.gemm_server.dto.common.response;

import com.example.gemm_server.common.enums.Category;
import com.example.gemm_server.domain.entity.Activity;
import com.example.gemm_server.domain.entity.Material;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import lombok.Getter;

@Getter
@Schema(description = "활동 상세 응답", requiredProperties = {"title", "materials", "age", "category",
    "contents"})
public class ActivityDetailResponse {

  @Schema(description = "제목")
  private String title;

  @Schema(description = "자료 리스트")
  private MaterialResponse[] materials;

  @Schema(description = "타겟 연령")
  private Short age;

  @Schema(description = "영역 및 활동")
  private Category category;

  @Schema(description = "내용과 형식")
  private ContentResponse[] contents;

  public ActivityDetailResponse(Activity activity, List<Material> materialsWithThumbnail) {
    this.title = activity.getTitle();
    this.materials = materialsWithThumbnail.stream().map(MaterialResponse::new)
        .toArray(MaterialResponse[]::new);
    this.age = activity.getAge();
    this.category = activity.getCategory();
    this.contents = ContentResponse.of(activity.getContent());
  }

}
