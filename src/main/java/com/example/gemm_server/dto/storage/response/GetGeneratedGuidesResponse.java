package com.example.gemm_server.dto.storage.response;

import com.example.gemm_server.domain.entity.Generation;
import com.example.gemm_server.dto.common.PageInfo;
import com.example.gemm_server.dto.common.response.PageInformationResponse;
import com.example.gemm_server.dto.storage.GeneratedGuideResponse;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import lombok.Getter;

@Getter
@Schema(description = "생성한 활동 방법 리스트 응답", requiredProperties = {"guides", "pageInfo"})
public class GetGeneratedGuidesResponse {

  @Schema(description = "생성한 활동 방법 리스트")
  private GeneratedGuideResponse[] guides;

  @Schema(description = "페이지 정보")
  private PageInformationResponse pageInfo;

  public GetGeneratedGuidesResponse(List<Generation> generations, PageInfo pageInfo) {
    this.guides = generations.stream().map(GeneratedGuideResponse::new)
        .toArray(GeneratedGuideResponse[]::new);
    this.pageInfo = new PageInformationResponse(pageInfo);
  }
}
