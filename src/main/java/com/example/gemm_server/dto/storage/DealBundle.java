package com.example.gemm_server.dto.storage;

import com.example.gemm_server.domain.entity.Deal;
import com.example.gemm_server.domain.entity.MarketItem;
import com.example.gemm_server.domain.entity.Thumbnail;
import com.example.gemm_server.dto.common.ActivityBundle;
import lombok.Getter;

@Getter
public class DealBundle extends ActivityBundle {

  private Deal deal;
  private MarketItem marketItem;

  public DealBundle(Deal dealWithActivity, Thumbnail thumbnail, MarketItem marketItem) {
    super(dealWithActivity.getActivity(), thumbnail);
    this.deal = dealWithActivity;
    this.marketItem = marketItem;
  }
}
