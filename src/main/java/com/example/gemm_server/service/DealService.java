package com.example.gemm_server.service;

import com.example.gemm_server.domain.entity.Deal;
import com.example.gemm_server.domain.repository.DealRepository;
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
    return dealRepository.findByBuyerIdOrderByCreatedAtDesc(memberId, pageable);
  }
}
