package com.example.gemm_server.service;

import com.example.gemm_server.domain.entity.MarketItem;
import com.example.gemm_server.domain.entity.Thumbnail;
import com.example.gemm_server.domain.repository.MarketItemRepository;
import com.example.gemm_server.dto.market.MarketItemBundle;
import java.util.List;
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
  private final ThumbnailService thumbnailService;
  private final ScrapService scrapService;

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

  public MarketItemBundle convertToMarketItemBundle(MarketItem marketItem, Long memberId) {
    Thumbnail thumbnail = thumbnailService.getMainThumbnail(marketItem.getActivity().getId());
    boolean isScrapped = scrapService.isScrapped(memberId, marketItem.getId());
    return new MarketItemBundle(marketItem, thumbnail, isScrapped);
  }

  public List<MarketItemBundle> convertToMarketItemBundle(List<MarketItem> marketItems,
      Long memberId) {
    return marketItems.stream().map(marketItem -> convertToMarketItemBundle(marketItem, memberId))
        .toList();
  }
}
