package com.example.gemm_server.dto.market.request;

import com.example.gemm_server.dto.common.request.ActivityDetailRequest;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateMarketItemRequest extends ActivityDetailRequest {

  @NotNull
  @Schema(description = "삭제할 자료의 id 리스트")
  private List<Long> deletedMaterialIds;
}