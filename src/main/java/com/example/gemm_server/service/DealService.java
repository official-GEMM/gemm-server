package com.example.gemm_server.service;

import static com.example.gemm_server.common.code.error.DealErrorCode.DEAL_NOT_FOUND;

import com.example.gemm_server.domain.entity.Deal;
import com.example.gemm_server.domain.repository.DealRepository;
import com.example.gemm_server.exception.DealException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DealService {

  private final DealRepository dealRepository;

  public Page<Deal> getDealsByMemberIdAndPage(Long memberId, int page, int limit) {
    Pageable pageable = PageRequest.of(page, limit);
    return dealRepository.findWithActivityByBuyerIdOrderByCreatedAtDesc(memberId, pageable);
  }

  public Deal getDealWithActivityOrThrow(Long dealId) {
    return dealRepository.findWithActivityById(dealId)
        .orElseThrow(() -> new DealException(DEAL_NOT_FOUND));
  }
}
