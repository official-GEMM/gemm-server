package com.example.gemm_server.service;

import static com.example.gemm_server.common.code.error.ScrapErrorCode.SCRAP_AlREADY_EXISTS;
import static com.example.gemm_server.common.code.error.ScrapErrorCode.SCRAP_NOT_FOUND_FOR_USER;

import com.example.gemm_server.domain.entity.MarketItem;
import com.example.gemm_server.domain.entity.Scrap;
import com.example.gemm_server.domain.repository.ScrapRepository;
import com.example.gemm_server.dto.market.MarketItemBundle;
import com.example.gemm_server.dto.my.ScrapBundle;
import com.example.gemm_server.exception.ScrapException;
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
public class ScrapService {

  private final ScrapRepository scrapRepository;
  private final MarketItemService marketItemService;

  public boolean isScrapped(Long memberId, Long marketItemId) {
    if (memberId == null) {
      return false;
    }
    return scrapRepository.existsByMemberIdAndMarketItemId(memberId, marketItemId);
  }

  public void delete(Long memberId, Long marketItemId) {
    List<Scrap> scraps = scrapRepository.findByMemberIdAndMarketItemId(memberId, marketItemId);
    if (scraps.isEmpty()) {
      throw new ScrapException(SCRAP_NOT_FOUND_FOR_USER);
    }
    scrapRepository.deleteAll(scraps);
  }

  public Scrap scrap(Long memberId, Long marketItemId) {
    if (isScrapped(memberId, marketItemId)) {
      throw new ScrapException(SCRAP_AlREADY_EXISTS);
    }
    Scrap scrap = Scrap.builder().memberId(memberId).marketItemId(marketItemId).build();
    return scrapRepository.save(scrap);
  }

  public Page<Scrap> getScrapsByMemberIdOrderByCreatedAt(Long memberId, Integer pageNumber,
      int pageSize) {
    Sort sort = Sort.by(Direction.DESC, "createdAt");
    Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
    return scrapRepository.findWithMarketItemByMemberId(memberId, pageable);
  }

  public ScrapBundle convertToScrapBundle(Scrap scrap, Long currentMemberId) {
    MarketItem marketItem = marketItemService.findMarketItemOrThrow(scrap.getMarketItem().getId());
    MarketItemBundle marketItemBundle = marketItemService.convertToMarketItemBundle(
        marketItem, currentMemberId);
    return new ScrapBundle(scrap, marketItemBundle);
  }

  public List<ScrapBundle> convertToScrapBundle(List<Scrap> scraps, Long currentMemberId) {
    return scraps.stream().map(scrap -> convertToScrapBundle(scrap, currentMemberId)).toList();
  }
}
