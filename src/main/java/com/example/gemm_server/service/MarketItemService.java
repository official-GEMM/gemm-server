package com.example.gemm_server.service;

import static com.example.gemm_server.common.code.error.MarketItemErrorCode.SEARCH_TYPE_NULL;

import com.example.gemm_server.common.enums.Order;
import com.example.gemm_server.domain.entity.MarketItem;
import com.example.gemm_server.domain.entity.Thumbnail;
import com.example.gemm_server.domain.repository.MarketItemRepository;
import com.example.gemm_server.dto.common.request.FilterRequest;
import com.example.gemm_server.dto.common.request.SearchRequest;
import com.example.gemm_server.dto.market.MarketItemBundle;
import com.example.gemm_server.exception.MarketItemException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MarketItemService {

  private final MarketItemRepository marketItemRepository;
  private final ThumbnailService thumbnailService;
  private final ScrapService scrapService;

  public Page<MarketItem> getMarketItemsOrderByScrapCountDesc(int pageNumber, int pageSize) {
    Sort sort = Order.SCRAP.sortBy();
    Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
    return marketItemRepository.findWithActivityAndOwnerBy(pageable);
  }

  public Page<MarketItem> getMarketItemsOrderByRecommendation(int pageNumber, int pageSize) {
    Sort sort = Order.RECOMMENDED.sortBy();
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

  public Page<MarketItem> searchMarketItems(SearchRequest search,
      FilterRequest filter, Order order, int pageNumber, Integer pageSize) {

    Pageable pageable = PageRequest.of(pageNumber, pageSize, order.sortBy());
    Specification<MarketItem> filterSpecification = generateSpecificationForFilter(filter);
    Specification<MarketItem> searchSpecification = generateSpecificationForSearch(search);

    List<MarketItem> results = marketItemRepository.findAll(
        filterSpecification.and(searchSpecification)); // TODO: 쿼리 최적화
    return new PageImpl<>(results, pageable, results.size());
  }

  public Specification<MarketItem> generateSpecificationForSearch(SearchRequest search) {
    if (search == null) {
      return MarketItemSpecification.alwaysTrue();
    }
    if (search.getType() == null) {
      throw new MarketItemException(SEARCH_TYPE_NULL);
    }
    return search.getType().getSpecification(search.getWord());
  }


  public Specification<MarketItem> generateSpecificationForFilter(FilterRequest filter) {
    if (filter == null) {
      return MarketItemSpecification.alwaysTrue();
    }

    return
        MarketItemSpecification.hasActivityAge(filter.getAges())
            .and(MarketItemSpecification.hasActivityCategory(filter.getCategories()))
            .and(MarketItemSpecification.hasActivityMaterialType(filter.getMaterialTypes()))
            .and(MarketItemSpecification.hasYear(filter.getYear()))
            .and(MarketItemSpecification.hasMonth(filter.getMonth()));
  }

}
