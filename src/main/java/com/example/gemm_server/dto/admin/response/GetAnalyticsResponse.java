package com.example.gemm_server.dto.admin.response;

import com.example.gemm_server.common.enums.Category;
import com.example.gemm_server.domain.entity.Analytics;
import com.example.gemm_server.dto.common.PageInfo;
import com.example.gemm_server.dto.common.response.PageInformationResponse;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Getter;

@Getter
@Schema(description = "분석 자료 리스트 응답", requiredProperties = {"analytics", "pageInfo"})
public class GetAnalyticsResponse {

  @Schema(description = "마켓 아이템 리스트")
  private AnalyticsResponse[] analytics;

  @Schema(description = "마켓 아이템 리스트")
  private PageInformationResponse pageInfo;

  @Getter
  @Schema(description = "지표 정보", requiredProperties = {
      "analyticsId, fileName, category, status, createdAt"})
  private static class AnalyticsResponse {

    @Schema(description = "지표 값")
    private long analyticsId;

    @Schema(description = "파일 이름")
    private String fileName;

    @Schema(description = "카테고리")
    private Category category;

    @Schema(description = "생성일")
    private LocalDateTime createdAt;

    public AnalyticsResponse(Analytics analytics) {
      this.analyticsId = analytics.getId();
      this.fileName = analytics.getFileName();
      this.category = analytics.getCategory();
      this.createdAt = analytics.getCreatedAt();
    }
  }

  public GetAnalyticsResponse(List<Analytics> analytics, PageInfo pageInfo) {
    this.analytics = analytics.stream().map(AnalyticsResponse::new)
        .toArray(AnalyticsResponse[]::new);
    this.pageInfo = new PageInformationResponse(pageInfo);
  }
}