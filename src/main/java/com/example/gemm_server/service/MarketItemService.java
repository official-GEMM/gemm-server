package com.example.gemm_server.service;

import static com.example.gemm_server.common.code.error.DealErrorCode.DEAL_AlREADY_EXISTS;
import static com.example.gemm_server.common.code.error.MarketItemErrorCode.CANNOT_PURCHASE_OWN_MARKET_ITEM;
import static com.example.gemm_server.common.code.error.MarketItemErrorCode.MARKET_ITEM_HAS_DEAL;
import static com.example.gemm_server.common.code.error.MarketItemErrorCode.MARKET_ITEM_NOT_FOUND;
import static com.example.gemm_server.common.code.error.MarketItemErrorCode.SEARCH_TYPE_NULL;

import com.example.gemm_server.common.enums.Order;
import com.example.gemm_server.domain.entity.Activity;
import com.example.gemm_server.domain.entity.MarketItem;
import com.example.gemm_server.domain.entity.Member;
import com.example.gemm_server.domain.entity.Thumbnail;
import com.example.gemm_server.domain.repository.DealRepository;
import com.example.gemm_server.domain.repository.MarketItemRepository;
import com.example.gemm_server.domain.repository.ScrapRepository;
import com.example.gemm_server.dto.common.request.FilterRequest;
import com.example.gemm_server.dto.common.request.SearchRequest;
import com.example.gemm_server.dto.market.MarketItemBundle;
import com.example.gemm_server.exception.DealException;
import com.example.gemm_server.exception.MarketItemException;
import jakarta.transaction.Transactional;
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
  private final ScrapRepository scrapRepository;
  private final DealRepository dealRepository;

  public Page<MarketItem> getMarketItemsOrderBy(int pageNumber, int pageSize, Sort sort) {
    Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
    return marketItemRepository.findWithActivityAndOwnerBy(pageable);
  }

  public Page<MarketItem> getMarketItemsByOwnerOrderBy(Long ownerId, int pageNumber, int pageSize,
      Sort sort) {
    Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
    return marketItemRepository.findWithActivityAndOwnerByOwnerId(ownerId, pageable);
  }

  public MarketItemBundle convertToMarketItemBundle(MarketItem marketItem, Long memberId) {
    Thumbnail thumbnail = thumbnailService.getMainThumbnail(marketItem.getActivity().getId());
    boolean isScrapped =
        memberId != null && scrapRepository.existsByMemberIdAndMarketItemId(memberId,
            marketItem.getId());
    return new MarketItemBundle(marketItem, thumbnail, isScrapped);
  }

  public List<MarketItemBundle> convertToMarketItemBundle(List<MarketItem> marketItems,
      Long memberId) {
    return marketItems.stream().map(marketItem -> convertToMarketItemBundle(marketItem, memberId))
        .toList();
  }

  public Page<MarketItem> searchMarketItems(SearchRequest search,
      FilterRequest filter, Order order, int pageNumber, Integer pageSize) {

    Pageable pageable = PageRequest.of(pageNumber, pageSize, order.getSort());
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

  public MarketItem findMarketItemOrThrow(Long marketItemId) {
    return this.marketItemRepository.findWithActivityAndOwnerById(marketItemId)
        .orElseThrow(() -> new MarketItemException(MARKET_ITEM_NOT_FOUND));
  }

  public Member findOwner(Long marketItemId) {
    return findMarketItemOrThrow(marketItemId).getOwner();
  }

  public void validatePurchasable(Long sellerId, Long buyerId, Long activityId) {
    boolean isPurchased = dealRepository.existsByActivityIdAndBuyerId(activityId, buyerId);
    if (isPurchased) {
      throw new DealException(DEAL_AlREADY_EXISTS);
    }
    if (sellerId.equals(buyerId)) {
      throw new MarketItemException(CANNOT_PURCHASE_OWN_MARKET_ITEM);
    }
  }

  public MarketItem delete(Long marketItemId) {
    MarketItem marketItem = findMarketItemOrThrow(marketItemId);
    marketItemRepository.delete(marketItem);
    return marketItem;
  }

  public MarketItem getMarketItemWithActivityOrThrow(Long marketItemId) {
    return marketItemRepository.findWithActivityById(marketItemId)
        .orElseThrow(() -> new MarketItemException(MARKET_ITEM_NOT_FOUND));
  }

  public MarketItem save(Activity activity, Long ownerId, int price, short year, short month) {
    MarketItem marketItem = MarketItem.createInitial(price, year, month, ownerId, activity);
    return marketItemRepository.save(marketItem);
  }

  public void validateUpdatable(Long activityId) {
    boolean hasDeal = dealRepository.existsByActivityId(activityId);
    if (hasDeal) {
      throw new MarketItemException(MARKET_ITEM_HAS_DEAL);
    }
  }

  @Transactional
  public MarketItem update(MarketItem marketItem, int price, short year, short month) {
    marketItem.setPrice(price);
    marketItem.setYear(year);
    marketItem.setMonth(month);
    return marketItemRepository.save(marketItem);
  }
}
