package com.example.gemm_server.service;

import static com.example.gemm_server.common.code.error.MemberErrorCode.MEMBER_ALREADY_COMPLETED;
import static com.example.gemm_server.common.code.error.MemberErrorCode.OWN_REFERRAL_CODE;
import static com.example.gemm_server.common.code.error.MemberErrorCode.REFERRAL_NOT_FOUND;

import com.example.gemm_server.common.util.DateUtil;
import com.example.gemm_server.domain.entity.Member;
import com.example.gemm_server.domain.repository.MemberRepository;
import com.example.gemm_server.dto.auth.request.PostNecessaryMemberDataRequest;
import com.example.gemm_server.exception.MemberException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MemberService {

  private final MemberRepository memberRepository;

  public Member getMemberByReferralCode(String referralCode) {
    return memberRepository.findOneByReferralCode(referralCode)
        .orElseThrow(() -> new MemberException(REFERRAL_NOT_FOUND));
  }

  public Member checkReferralCompenstableAndGetMember(Long memberId, String referralCode) {
    Member currentMember = memberRepository.findOneById(memberId);
    if (currentMember.isDataCompleted()) {
      throw new MemberException(MEMBER_ALREADY_COMPLETED);
    }
    if (referralCode.equals(currentMember.getReferralCode())) {
      throw new MemberException(OWN_REFERRAL_CODE);
    }
    return currentMember;
  }

  @Transactional
  public Member updateNecessaryMemberData(Long memberId,
      PostNecessaryMemberDataRequest memberInfo) {
    Member member = memberRepository.findOneById(memberId);

    member.setName(memberInfo.getName());
    member.setBirth(DateUtil.parseYearMonthDay(memberInfo.getBirth()));
    member.setNickname(memberInfo.getNickname());
    member.setPhoneNumber(memberInfo.getPhoneNumber());
    member.setManageAge(memberInfo.getManageAge());
    return member;
  }
}