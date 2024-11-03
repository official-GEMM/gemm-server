package com.example.gemm_server.common.enums;

import com.example.gemm_server.domain.entity.MarketItem;
import com.example.gemm_server.service.MarketItemSpecification;
import lombok.Getter;
import org.springframework.data.jpa.domain.Specification;

@Getter
public enum SearchType {
  TOTAL("전체") {
    public Specification<MarketItem> getSpecification(String word) {
      return MarketItemSpecification.hasOwner(word).or(MarketItemSpecification.hasTitle(word));
    }
  },
  TITLE("제목") {
    public Specification<MarketItem> getSpecification(String word) {
      return MarketItemSpecification.hasTitle(word);
    }
  },
  SELLER("판매자") {
    public Specification<MarketItem> getSpecification(String word) {
      return MarketItemSpecification.hasOwner(word);
    }
  };

  private final String description;

  public abstract Specification<MarketItem> getSpecification(String word);

  SearchType(String description) {
    this.description = description;
  }
}