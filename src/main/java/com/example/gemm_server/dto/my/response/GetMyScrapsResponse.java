package com.example.gemm_server.dto.my.response;

import com.example.gemm_server.dto.common.PageInfo;
import com.example.gemm_server.dto.common.response.PageInformationResponse;
import com.example.gemm_server.dto.my.ScrapBundle;
import com.example.gemm_server.dto.my.ScrapResponse;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import lombok.Getter;

@Getter
@Schema(description = "내 스크립 리스트 응답", requiredProperties = {"scraps", "pageInfo"})
public class GetMyScrapsResponse {

  @Schema(description = "내 스크랩 리스트")
  private ScrapResponse[] scraps;

  @Schema(description = "페이지 정보")
  private PageInformationResponse pageInfo;

  public GetMyScrapsResponse(List<ScrapBundle> scraps, PageInfo pageInfo) {
    this.scraps = scraps.stream().map(ScrapResponse::new).toArray(ScrapResponse[]::new);
    this.pageInfo = new PageInformationResponse(pageInfo);
  }
}
