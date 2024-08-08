package com.example.gemm_server.service;

import static com.example.gemm_server.common.code.error.MemberErrorCode.MEMBER_ALREADY_COMPLETED;
import static com.example.gemm_server.common.code.error.MemberErrorCode.MEMBER_NOT_FOUND;
import static com.example.gemm_server.common.code.error.MemberErrorCode.OWN_REFERRAL_CODE;
import static com.example.gemm_server.common.code.error.MemberErrorCode.REFERRAL_NOT_FOUND;

import com.example.gemm_server.common.util.DateUtil;
import com.example.gemm_server.domain.entity.Member;
import com.example.gemm_server.domain.repository.MemberRepository;
import com.example.gemm_server.dto.auth.request.PostNecessaryMemberDataRequest;
import com.example.gemm_server.dto.my.request.UpdateMyInformationRequest;
import com.example.gemm_server.exception.MemberException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MemberService {

  private final MemberRepository memberRepository;

  public Member findMemberByMemberIdOrThrow(Long memberId) {
    return memberRepository.findOneById(memberId)
        .orElseThrow(() -> new MemberException(MEMBER_NOT_FOUND));
  }

  public Member getMemberByReferralCode(String referralCode) {
    return memberRepository.findOneByReferralCode(referralCode)
        .orElseThrow(() -> new MemberException(REFERRAL_NOT_FOUND));
  }

  public Member checkReferralCompenstableAndGetMember(Long memberId, String referralCode) {
    Member currentMember = findMemberByMemberId(memberId);
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
    Member member = findMemberByMemberIdOrThrow(memberId);

    member.setName(memberInfo.getName());
    member.setBirth(DateUtil.parseYearMonthDay(memberInfo.getBirth()));
    member.setNickname(memberInfo.getNickname());
    member.setPhoneNumber(memberInfo.getPhoneNumber());
    member.setManageAge(memberInfo.getManageAge());
    return member;
  }

  public boolean isNicknameExists(String nickname) {
    return memberRepository.existsByNickname(nickname);
  }
  
  @Transactional
  public Member updateMyInformation(Long memberId, UpdateMyInformationRequest memberInfo) {
    Member member = findMemberByMemberIdOrThrow(memberId);

    member.setManageAge(memberInfo.getManageAge());
    member.setPhoneNumber(memberInfo.getPhoneNumber());
    return member;
  }

  @Transactional
  public String updateNickname(Long memberId, String nickname) {
    Member member = findMemberByMemberIdOrThrow(memberId);

    member.setNickname(nickname);
    return member.getNickname();
  }

  public void withdrawMember(Long memberId) {
    // TODO: 해당 회원의 마켓 자료 삭제
    deleteMember(memberId);
  }

  public void deleteMember(Long memberId) {
    memberRepository.deleteById(memberId);
  }
}