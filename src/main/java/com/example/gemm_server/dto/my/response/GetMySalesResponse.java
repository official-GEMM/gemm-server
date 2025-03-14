package com.example.gemm_server.dto.my.response;

import com.example.gemm_server.domain.entity.Activity;
import com.example.gemm_server.domain.entity.Deal;
import com.example.gemm_server.dto.common.PageInfo;
import com.example.gemm_server.dto.common.response.PageInformationResponse;
import com.example.gemm_server.dto.storage.DealBundle;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Getter;

@Getter
@Schema(description = "내 판매 내역 응답", requiredProperties = {"sales", "pageInfo"})
public class GetMySalesResponse {

  @Schema(description = "구매 내역")
  private Sale[] sales;

  @Schema(description = "페이지 정보")
  private PageInformationResponse pageInfo;

  @Getter
  @Schema(description = "판매 정보", requiredProperties = {"dealId", "marketItemId", "title",
      "thumbnailPath", "buyerNickname", "soldAt", "price"})
  private static class Sale {

    @Schema(description = "거래 아이디")
    private long dealId;

    @Schema(description = "마켓 상품 아이디")
    private long marketItemId;

    @Schema(description = "제목")
    private String title;

    @Schema(description = "썸네일 경로")
    private String thumbnailPath;

    @Schema(description = "구매자 닉네임")
    private String buyerNickname;

    @Schema(description = "판매 일시")
    private LocalDateTime soldAt;

    @Schema(description = "가격")
    private int price;

    public Sale(DealBundle dealBundle) {
      Deal deal = dealBundle.getDeal();
      Activity activity = dealBundle.getActivity();
      this.dealId = deal.getId();
      this.marketItemId = dealBundle.getMarketItem().getId();
      this.thumbnailPath = dealBundle.getThumbnailPath();
      this.buyerNickname = deal.getBuyer().getNickname();
      this.title = activity.getTitle();
      this.soldAt = deal.getCreatedAt();
      this.price = deal.getPrice();
    }
  }

  public GetMySalesResponse(List<DealBundle> dealBundles, PageInfo pageInfo) {
    this.sales = dealBundles.stream().map(Sale::new).toArray(Sale[]::new);
    this.pageInfo = new PageInformationResponse(pageInfo);
  }
}