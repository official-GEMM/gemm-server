package com.example.gemm_server.dto.admin.response;

import com.example.gemm_server.common.util.S3Util;
import com.example.gemm_server.domain.entity.Analytics;
import com.example.gemm_server.dto.admin.AnalyticsAverage;
import com.example.gemm_server.dto.admin.ComparisonIndicator;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;
import lombok.Getter;

@Getter
@Schema(description = "분석 자료 상세 응답", requiredProperties = {"analyticsId", "fileName", "filePath",
    "nickname", "createdAt", "status"})
public class GetAnalyticsDetailResponse {

  @Schema(description = "지표 값")
  private long analyticsId;

  @Schema(description = "파일 이름")
  private String fileName;

  @Schema(description = "파일 경로")
  private String filePath;

  @Schema(description = "사용자 이름")
  private String nickname;

  @Schema(description = "생성일")
  private LocalDateTime createdAt;

  @Schema(description = "상태")
  private Status status;

  @Getter
  @Schema(description = "지표 정보", requiredProperties = {"layoutCompleteness", "readability",
      "engagementInducement", "generationTime"})
  private static class Status {

    @Schema(description = "레이아웃 완성도")
    private ComparisonIndicator layoutCompleteness;

    @Schema(description = "가독성")
    private ComparisonIndicator readability;

    @Schema(description = "생성에 걸린 시간")
    private ComparisonIndicator generationTime;

    public Status(AnalyticsAverage month, AnalyticsAverage week, Analytics current) {
      this.layoutCompleteness = new ComparisonIndicator(
          month.getLayoutCompletenessAvg().floatValue(),
          week.getLayoutCompletenessAvg().floatValue(), current.getLayoutCompleteness());
      this.readability = new ComparisonIndicator(month.getReadabilityAvg().floatValue(),
          week.getReadabilityAvg().floatValue(), current.getReadability());
      this.generationTime = new ComparisonIndicator(month.getGenerationTimeAvg().floatValue(),
          week.getGenerationTimeAvg().floatValue(), current.getGenerationTime());
    }

  }

  public GetAnalyticsDetailResponse(AnalyticsAverage month, AnalyticsAverage week,
      Analytics current) {
    this.analyticsId = current.getId();
    this.fileName = current.getFileName();
    this.filePath = S3Util.getFileUrl(current.getDirectoryPath() + current.getFileName());
    this.nickname = current.getNickname();
    this.createdAt = current.getCreatedAt();
    this.status = new Status(month, week, current);
  }
}