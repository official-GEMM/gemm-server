package com.example.gemm_server.dto.admin.response;

import com.example.gemm_server.common.enums.ChangeType;
import com.example.gemm_server.common.enums.IndicatorType;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.Optional;
import lombok.Getter;

@Getter
@Schema(description = "지표 정보", requiredProperties = {"value"})
public class IndicatorResponse {

  @Schema(description = "지표 값")
  private Float value;

  @JsonInclude(JsonInclude.Include.NON_NULL)
  @Schema(description = "변화량")
  private Float change;

  @JsonInclude(JsonInclude.Include.NON_NULL)
  @Schema(description = "변화 종류")
  private ChangeType changeType;

  public IndicatorResponse(Double lastValue, Double nextValue, IndicatorType indicatorType) {
    if (lastValue == null || nextValue == null) {
      this.value = Optional.ofNullable(nextValue).map(Number::floatValue).orElse(null);
    } else {
      Float last = lastValue.floatValue();
      Float next = nextValue.floatValue();

      this.value = next;
      this.change = next - last;
      this.changeType = ChangeType.getChangeType(last, next, indicatorType);
    }
  }
}
