package com.example.gemm_server.dto.common.response;

import com.example.gemm_server.domain.entity.Activity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
@Schema(description = "활동 방법 상세 응답", requiredProperties = {"title", "content", "age", "category"})
public class GuideDetailResponse extends GuideResponse {

  public GuideDetailResponse(Activity activity) {
    super(activity);
  }
}
