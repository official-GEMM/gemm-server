package com.example.gemm_server.service;

import static com.example.gemm_server.common.code.error.MemberErrorCode.MEMBER_ALREADY_COMPLETED;
import static com.example.gemm_server.common.code.error.MemberErrorCode.OWN_REFERRAL_CODE;
import static com.example.gemm_server.common.code.error.MemberErrorCode.REFERRAL_NOT_FOUND;
import static com.example.gemm_server.common.constant.Policy.ATTENDANCE_COMPENSATION;
import static com.example.gemm_server.common.constant.Policy.REFERRAL_COMPENSATION;

import com.example.gemm_server.common.enums.GemUsage;
import com.example.gemm_server.common.util.DateUtil;
import com.example.gemm_server.domain.entity.Member;
import com.example.gemm_server.domain.repository.MemberRepository;
import com.example.gemm_server.dto.member.NecessaryMemberDataPostRequest;
import com.example.gemm_server.exception.MemberException;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthService {

  private final MemberRepository memberRepository;
  private final GemService gemService;

  @Transactional
  public Member compensateMemberForDailyAttendance(Long memberId) {
    Member member = memberRepository.findOneById(memberId);
    if (!DateUtil.isToday(member.getLastLoginAt())) {
      gemService.saveChangesOfGemWithMember(member, ATTENDANCE_COMPENSATION, GemUsage.COMPENSATION);
    }
    member.setLastLoginAt(LocalDateTime.now());
    return member;
  }

  @Transactional
  public void compensateMemberForReferral(Long currentMemberId, String referralCode) {
    Member currentMember = checkReferralCompenstableAndGetMember(currentMemberId,
        referralCode);
    Member referralMember = getMemberByReferralCode(referralCode);

    gemService.saveChangesOfGemWithMember(currentMember, REFERRAL_COMPENSATION,
        GemUsage.COMPENSATION);
    gemService.saveChangesOfGemWithMember(referralMember, REFERRAL_COMPENSATION,
        GemUsage.COMPENSATION);
  }

  public void updateNecessaryMemberData(Long memberId,
      NecessaryMemberDataPostRequest memberInfo) {
    Member member = memberRepository.findOneById(memberId);

    member.setName(memberInfo.getName());
    member.setBirth(DateUtil.parseYearMonthDay(memberInfo.getBirth()));
    member.setNickname(memberInfo.getNickname());
    member.setPhoneNumber(memberInfo.getPhoneNumber());
    member.setManageAge(memberInfo.getManageAge());
  }

  private Member getMemberByReferralCode(String referralCode) {
    return memberRepository.findOneByReferralCode(referralCode)
        .orElseThrow(() -> new MemberException(REFERRAL_NOT_FOUND));
  }

  private Member checkReferralCompenstableAndGetMember(Long memberId, String referralCode) {
    Member currentMember = memberRepository.findOneById(memberId);
    if (currentMember.isDataCompleted()) {
      throw new MemberException(MEMBER_ALREADY_COMPLETED);
    }
    if (referralCode.equals(currentMember.getReferralCode())) {
      throw new MemberException(OWN_REFERRAL_CODE);
    }
    return currentMember;
  }
}
