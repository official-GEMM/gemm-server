package com.example.gemm_server.service;

import static com.example.gemm_server.common.code.error.ScrapErrorCode.SCRAP_AlREADY_EXISTS;
import com.example.gemm_server.domain.entity.Scrap;
import com.example.gemm_server.domain.repository.ScrapRepository;
import com.example.gemm_server.exception.ScrapException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ScrapService {

  private final ScrapRepository scrapRepository;

  public boolean isScrapped(Long memberId, Long marketItemId) {
    if (memberId == null) {
      return false;
    }
    return scrapRepository.existsByMemberIdAndMarketItemId(memberId, marketItemId);
  }

  public Scrap scrap(Long memberId, Long marketItemId) {
    if (isScrapped(memberId, marketItemId)) {
      throw new ScrapException(SCRAP_AlREADY_EXISTS);
    }
    Scrap scrap = Scrap.builder().memberId(memberId).marketItemId(marketItemId).build();
    return scrapRepository.save(scrap);
  }
}
