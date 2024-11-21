package com.example.gemm_server.dto.admin;

import com.example.gemm_server.common.util.NumberUtil;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
@Schema(description = "비교 지표", requiredProperties = {"week, month, current"})
public class ComparisonIndicator {

  @Schema(description = "주 평균 지표 값")
  private float week;

  @Schema(description = "월 평균 지표 값")
  private float month;

  @Schema(description = "현재 지표 값")
  private float current;

  public ComparisonIndicator(float month, float week, float current) {
    this.week = NumberUtil.roundToDecimalPlaces(week, 2);
    this.month = NumberUtil.roundToDecimalPlaces(month, 2);
    this.current = NumberUtil.roundToDecimalPlaces(current, 2);
  }
}
