package com.example.gemm_server.dto.market;

import com.example.gemm_server.domain.entity.MarketItem;
import com.example.gemm_server.domain.entity.Member;
import com.example.gemm_server.domain.entity.Thumbnail;
import com.example.gemm_server.dto.common.ActivityBundle;
import lombok.Getter;

@Getter
public class MarketItemBundle extends ActivityBundle {

  private MarketItem marketItem;
  private Member seller;
  private boolean isScrapped;

  public MarketItemBundle(MarketItem marketItemWithActivityAndOwner, Thumbnail mainThumbnail,
      boolean isScrapped) {
    super(marketItemWithActivityAndOwner.getActivity(), mainThumbnail);
    this.seller = marketItemWithActivityAndOwner.getOwner();
    this.marketItem = marketItemWithActivityAndOwner;
    this.isScrapped = isScrapped;
  }
}
