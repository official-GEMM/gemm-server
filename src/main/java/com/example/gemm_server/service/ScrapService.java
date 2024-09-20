package com.example.gemm_server.service;

import com.example.gemm_server.domain.repository.ScrapRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ScrapService {

  private final ScrapRepository scrapRepository;

  public boolean isScrapped(Long memberId, Long marketItemId) {
    return scrapRepository.existsByMemberIdAndMarketItemId(memberId, marketItemId);
  }
}
