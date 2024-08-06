package com.example.gemm_server.dto.market.response;

import com.example.gemm_server.dto.common.response.MemberWithProfileImageResponse;
import com.example.gemm_server.dto.common.response.PageInformationResponse;
import com.example.gemm_server.dto.market.MarketItemResponse;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
@Schema(description = "판매자의 상품 리스트 응답", requiredProperties = {"marketItems", "seller",
    "pageInfo"})
public class GetMarketItemsOfSellerResponse {

  @Schema(description = "마켓 아이템 리스트")
  private MarketItemResponse[] marketItems;

  @Schema(description = "판매자")
  private MemberWithProfileImageResponse seller;

  @Schema(description = "페이지 정보")
  private PageInformationResponse pageInfo;

  public GetMarketItemsOfSellerResponse() {
  }
}
