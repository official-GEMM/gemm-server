package com.example.gemm_server.dto.common.request;

import com.example.gemm_server.common.enums.SearchType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SearchRequest {

  @Schema(description = "검색어")
  @NotNull
  private String word;

  @Schema(description = "검색 종류")
  @NotNull
  private SearchType type;
}
