package com.example.gemm_server.service;

import com.example.gemm_server.domain.entity.MarketItem;
import com.example.gemm_server.domain.repository.MarketItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MarketItemService {

  private final MarketItemRepository marketItemRepository;

  public Page<MarketItem> getMarketItemsOrderByScrapCountDesc(int pageNumber, int pageSize) {
    Sort sort = Sort.by(Direction.DESC, "scrapCount");
    Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
    return marketItemRepository.findWithActivityAndOwnerBy(pageable);
  }

  public Page<MarketItem> getMarketItemsOrderByRecommendation(int pageNumber, int pageSize) {
    Sort sort = Sort.by(Direction.DESC, "scrapCount"); // TODO: 추천 공식 정리
    Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
    return marketItemRepository.findWithActivityAndOwnerBy(pageable);
  }
}
