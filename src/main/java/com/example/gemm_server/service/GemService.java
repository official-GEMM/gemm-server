package com.example.gemm_server.service;

import static com.example.gemm_server.common.code.error.GemErrorCode.GEM_TO_BE_POSITIVE;
import static com.example.gemm_server.common.code.error.MemberErrorCode.MEMBER_HAS_NEGATIVE_GEM;

import com.example.gemm_server.common.code.error.GemErrorCode;
import com.example.gemm_server.common.enums.GemUsageType;
import com.example.gemm_server.domain.entity.Gem;
import com.example.gemm_server.domain.entity.Member;
import com.example.gemm_server.domain.repository.GemRepository;
import com.example.gemm_server.domain.repository.MemberRepository;
import com.example.gemm_server.exception.GemException;
import com.example.gemm_server.exception.MemberException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class GemService {

  private final GemRepository gemRepository;
  private final MemberRepository memberRepository;

  @Transactional
  public Gem saveChangesOfGemWithMember(Member member, int amount, GemUsageType usageType) {
    // TODO: 필요시 gem 변경 대한 알림 생성
    int remainGem = getRemainGem(member, amount, usageType);
    member.setGem(remainGem);
    memberRepository.save(member);
    return saveGem(amount, usageType, member.getId());
  }

  public int getRemainGem(Member member, int usageAmount, GemUsageType usageType) {
    if (usageAmount < 0) {
      throw new GemException(GEM_TO_BE_POSITIVE);
    }
    if (member.getGem() < 0) {
      throw new MemberException(MEMBER_HAS_NEGATIVE_GEM);
    }
    if (member.getGem() + usageType.getSignedAmount(usageAmount) < 0) {
      throw new GemException(GemErrorCode.NOT_ENOUGH_GEM);
    }
    return member.getGem() + usageType.getSignedAmount(usageAmount);
  }

  private Gem saveGem(int amount, GemUsageType usageType, Long memberId) {
    Gem gem = new Gem(amount, usageType, memberId);
    return gemRepository.save(gem);
  }
}
