package com.example.gemm_server.dto.my;

import com.example.gemm_server.domain.entity.Scrap;
import com.example.gemm_server.dto.market.MarketItemBundle;
import lombok.Getter;

@Getter
public class ScrapBundle {

  private Scrap scrap;
  private MarketItemBundle marketItemBundle;

  public ScrapBundle(Scrap scrap, MarketItemBundle marketItemBundle) {
    this.scrap = scrap;
    this.marketItemBundle = marketItemBundle;
  }

}
