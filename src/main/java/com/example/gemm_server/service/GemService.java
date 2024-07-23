package com.example.gemm_server.service;

import com.example.gemm_server.common.enums.GemUsage;
import com.example.gemm_server.domain.entity.Gem;
import com.example.gemm_server.domain.entity.Member;
import com.example.gemm_server.domain.repository.GemRepository;
import com.example.gemm_server.domain.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GemService {

  private final GemRepository gemRepository;
  private final MemberRepository memberRepository;

  public void saveChangesOfGemWithMember(Member member, int amount, GemUsage usageType) {
    // TODO: 필요시 gem 변경 대한 알림 생성
    member.setGem(member.getGem() + usageType.getSignedAmount(amount));
    memberRepository.save(member);
    
    Gem gem = new Gem(amount, usageType, member.getId());
    gemRepository.save(gem);
  }
}
