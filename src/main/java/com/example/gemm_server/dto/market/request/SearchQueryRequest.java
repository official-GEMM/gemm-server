package com.example.gemm_server.dto.market.request;

import com.example.gemm_server.common.enums.Order;
import com.example.gemm_server.dto.common.request.FilterRequest;
import com.example.gemm_server.dto.common.request.SearchRequest;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SearchQueryRequest {

  @Schema(description = "검색")
  private SearchRequest search;

  @Schema(description = "필터")
  private FilterRequest filter;

  @Schema(description = "정렬")
  @NotNull
  private Order order;

  @Schema(description = "페이지")
  @NotNull
  private Integer page;
}