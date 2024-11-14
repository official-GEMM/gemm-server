package com.example.gemm_server.dto.admin.response;

import com.example.gemm_server.common.enums.IndicatorType;
import com.example.gemm_server.dto.admin.AnalyticsAverage;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
@Schema(description = "분석 지표 응답", requiredProperties = {"layoutCompleteness", "readability",
    "generationTime"})
public class GetAnalyticsStatusResponse {

  @Schema(description = "마켓 아이템 리스트")
  private IndicatorResponse layoutCompleteness;

  @Schema(description = "마켓 아이템 리스트")
  private IndicatorResponse readability;

  @Schema(description = "마켓 아이템 리스트")
  private IndicatorResponse generationTime;

  public GetAnalyticsStatusResponse(AnalyticsAverage last, AnalyticsAverage recent) {
    this.layoutCompleteness = new IndicatorResponse(
        last.getLayoutCompletenessAvg(),
        recent.getLayoutCompletenessAvg(),
        IndicatorType.LAYOUT_COMPLETENESS);
    this.readability = new IndicatorResponse(
        last.getReadabilityAvg(),
        recent.getReadabilityAvg(),
        IndicatorType.READABILITY);
    this.generationTime = new IndicatorResponse(
        last.getGenerationTimeAvg(),
        recent.getGenerationTimeAvg(),
        IndicatorType.GENERATION_TIME);
  }
}
