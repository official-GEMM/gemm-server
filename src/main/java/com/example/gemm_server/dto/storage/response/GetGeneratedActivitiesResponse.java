package com.example.gemm_server.dto.storage.response;

import com.example.gemm_server.dto.common.PageInfo;
import com.example.gemm_server.dto.common.response.PageInformationResponse;
import com.example.gemm_server.dto.storage.GeneratedActivityResponse;
import com.example.gemm_server.dto.storage.GenerationWithThumbnail;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import lombok.Getter;

@Getter
@Schema(description = "생성한 활동 리스트 응답", requiredProperties = {"activities", "pageInfo"})
public class GetGeneratedActivitiesResponse {

  @Schema(description = "생성한 활동 리스트")
  private GeneratedActivityResponse[] activities;

  @Schema(description = "페이지 정보")
  private PageInformationResponse pageInfo;

  public GetGeneratedActivitiesResponse(List<GenerationWithThumbnail> generations,
      PageInfo pageInfo) {
    this.activities = generations.stream().map(GeneratedActivityResponse::new)
        .toArray(GeneratedActivityResponse[]::new);
    this.pageInfo = new PageInformationResponse(pageInfo);
  }
}
