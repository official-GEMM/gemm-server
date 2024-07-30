package com.example.gemm_server.dto.market.request;

import com.example.gemm_server.dto.common.request.ActivityDetailRequest;
import com.example.gemm_server.dto.common.request.MaterialRequest;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateMarketItemRequest extends ActivityDetailRequest {

  @Schema(description = "연도")
  private int year;

  @Min(1)
  @Max(12)
  @Schema(description = "월")
  private short month;

  @NotNull
  @JsonProperty("addedMaterials")
  @Schema(description = "추가할 자료 리스트")
  private List<MaterialRequest> materials;

  @NotNull
  @Schema(description = "삭제할 자료의 id 리스트")
  private List<Integer> deletedMaterialIds;
}