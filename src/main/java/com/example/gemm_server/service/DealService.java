package com.example.gemm_server.service;

import static com.example.gemm_server.common.code.error.DealErrorCode.DEAL_NOT_FOUND;

import com.example.gemm_server.domain.entity.Deal;
import com.example.gemm_server.domain.entity.MarketItem;
import com.example.gemm_server.domain.entity.Thumbnail;
import com.example.gemm_server.domain.repository.DealRepository;
import com.example.gemm_server.domain.repository.MarketItemRepository;
import com.example.gemm_server.dto.storage.DealBundle;
import com.example.gemm_server.exception.DealException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.YearMonth;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DealService {

  private final DealRepository dealRepository;
  private final ThumbnailService thumbnailService;
  private final MarketItemRepository marketItemRepository;

  public Page<Deal> getDealsByMemberId(Long memberId, int pageNumber, int pageSize, Sort sort) {
    Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
    return dealRepository.findWithActivityByBuyerId(memberId, pageable);
  }

  public Page<Deal> getDealsByBuyerId(Long buyerId, int pageNumber, int pageSize, Sort sort,
      int year, short month) {
    Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
    LocalDateTime startDate = YearMonth.of(year, month).atDay(1).atStartOfDay();
    LocalDateTime endDate = YearMonth.of(year, month).atEndOfMonth().atTime(LocalTime.MAX);
    return dealRepository.findWithActivityByBuyerIdAndCreatedAtBetween(buyerId, startDate, endDate,
        pageable);
  }

  public Page<Deal> getDealsBySellerId(Long sellerId, int pageNumber, int pageSize, Sort sort,
      int year, short month) {
    Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);

    LocalDateTime startDate = YearMonth.of(year, month).atDay(1).atStartOfDay();
    LocalDateTime endDate = YearMonth.of(year, month).atEndOfMonth().atTime(LocalTime.MAX);
    return dealRepository.findWithActivityAndBuyerBySellerIdAndCreatedAtBetween(sellerId, startDate,
        endDate, pageable);
  }

  public Deal getDealWithActivityOrThrow(Long dealId) {
    return dealRepository.findWithActivityById(dealId)
        .orElseThrow(() -> new DealException(DEAL_NOT_FOUND));
  }

  public DealBundle convertToDealBundle(Deal deal) {
    Long activityId = deal.getActivity().getId();
    Thumbnail thumbnail = thumbnailService.getMainThumbnail(activityId);
    MarketItem marketItem = marketItemRepository.findByActivityId(activityId);
    return new DealBundle(deal, thumbnail, marketItem);
  }

  public List<DealBundle> convertToDealBundle(List<Deal> deals) {
    return deals.stream().map(this::convertToDealBundle).toList();
  }

  public Boolean isPurchased(Long buyerId, Long activityId) {
    if (buyerId == null) {
      return false;
    }
    return dealRepository.existsByActivityIdAndBuyerId(activityId, buyerId);
  }

  public Deal savePurchaseForMarketItem(MarketItem marketItemWithActivityAndOwner, Long buyerId) {
    Deal deal = new Deal(marketItemWithActivityAndOwner, buyerId);
    return dealRepository.save(deal);
  }
}
