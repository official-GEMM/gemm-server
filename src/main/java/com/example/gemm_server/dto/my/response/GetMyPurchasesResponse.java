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
@Schema(description = "내 구매 내역 응답", requiredProperties = {"purchases", "pageInfo"})
public class GetMyPurchasesResponse {

  @Schema(description = "구매 내역")
  private Purchase[] purchases;

  @Schema(description = "페이지 정보")
  private PageInformationResponse pageInfo;

  @Getter
  @Schema(description = "구매 정보", requiredProperties = {"dealId", "marketItemId", "title",
      "thumbnailPath", "buyAt", "price"})
  private static class Purchase {

    @Schema(description = "거래 아이디")
    private long dealId;

    @Schema(description = "마켓 상품 아이디")
    private long marketItemId;

    @Schema(description = "제목")
    private String title;

    @Schema(description = "썸네일 경로")
    private String thumbnailPath;

    @Schema(description = "구매 일시")
    private LocalDateTime buyAt;

    @Schema(description = "가격")
    private int price;

    public Purchase(DealBundle dealBundle) {
      Deal deal = dealBundle.getDeal();
      Activity activity = dealBundle.getActivity();
      this.dealId = deal.getId();
      this.marketItemId = dealBundle.getMarketItem().getId();
      this.thumbnailPath = dealBundle.getThumbnailPath();
      this.title = activity.getTitle();
      this.buyAt = deal.getCreatedAt();
      this.price = deal.getPrice();
    }
  }

  public GetMyPurchasesResponse(List<DealBundle> dealBundles, PageInfo pageInfo) {
    this.purchases = dealBundles.stream().map(Purchase::new).toArray(Purchase[]::new);
    this.pageInfo = new PageInformationResponse(pageInfo);
  }
}