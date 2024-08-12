package com.example.gemm_server.dto.common.response;

import com.example.gemm_server.common.enums.Category;
import com.example.gemm_server.common.enums.MaterialType;
import com.example.gemm_server.common.util.MaterialUtil;
import com.example.gemm_server.domain.entity.Activity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
@Schema(description = "활동 응답", requiredProperties = {"title", "thumbnailPath", "age", "category",
    "materialType"})
public class ActivityResponse {

  @Schema(description = "제목")
  private String title;

  @Schema(description = "썸네일 경로")
  private String thumbnailPath;

  @Schema(description = "타겟 연령")
  private int age;

  @Schema(description = "영역 및 활동")
  private Category category;

  @Schema(description = "보유한 자료 종류")
  private MaterialType[] materialType;

  public ActivityResponse(Activity activity, String thumbnailPath) {
    this.title = activity.getTitle();
    this.thumbnailPath = thumbnailPath;
    this.age = activity.getAge();
    this.category = activity.getCategory();
    this.materialType = MaterialUtil.binaryToList(activity.getMaterialType())
        .toArray(MaterialType[]::new);

  }
}
