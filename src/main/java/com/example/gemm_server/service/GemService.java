package com.example.gemm_server.service;

import com.example.gemm_server.common.enums.GemUsageType;
import com.example.gemm_server.domain.entity.Gem;
import com.example.gemm_server.domain.entity.Member;
import com.example.gemm_server.domain.repository.GemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class GemService {

  private final GemRepository gemRepository;

  @Transactional
  public Gem saveChangesOfGemWithMember(Member member, int amount, GemUsageType usageType) {
    // TODO: 필요시 gem 변경 대한 알림 생성
    member.setGem(member.getGem() + usageType.getSignedAmount(amount));
    return saveGem(amount, usageType, member.getId());
  }

  private Gem saveGem(int amount, GemUsageType usageType, Long memberId) {
    Gem gem = new Gem(amount, usageType, memberId);
    return gemRepository.save(gem);
  }
}
