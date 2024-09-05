package com.example.gemm_server.service;

import static com.example.gemm_server.common.code.error.MemberErrorCode.MEMBER_ALREADY_COMPLETED;
import static com.example.gemm_server.common.code.error.MemberErrorCode.OWN_REFERRAL_CODE;
import static com.example.gemm_server.common.constant.Policy.ATTENDANCE_COMPENSATION;
import static com.example.gemm_server.common.constant.Policy.REFERRAL_COMPENSATION;

import com.example.gemm_server.common.constant.TimeZone;
import com.example.gemm_server.common.enums.GemUsageType;
import com.example.gemm_server.common.util.DateUtil;
import com.example.gemm_server.domain.entity.Member;
import com.example.gemm_server.domain.entity.redis.TokenBlackList;
import com.example.gemm_server.domain.repository.redis.RefreshTokenRepository;
import com.example.gemm_server.domain.repository.redis.TokenBlackListRepository;
import com.example.gemm_server.dto.auth.MemberCompensation;
import com.example.gemm_server.exception.MemberException;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthService {

  private final MemberService memberService;
  private final GemService gemService;
  private final TokenBlackListRepository tokenBlackListRepository;
  private final RefreshTokenRepository refreshTokenRepository;
  private final NotificationService notificationService;

  @Transactional
  public void logout(Long memberId, String token) {
    TokenBlackList blackList = new TokenBlackList(token);
    tokenBlackListRepository.save(blackList);

    refreshTokenRepository.deleteById(memberId);
  }

  @Transactional
  public MemberCompensation compensateMemberForDailyAttendance(Long memberId) {
    Member member = memberService.findMemberByMemberIdOrThrow(memberId);
    boolean isCompensated = false;
    if (!DateUtil.isToday(member.getLastLoginAt())) {
      gemService.saveChangesOfGemWithMember(member, ATTENDANCE_COMPENSATION,
          GemUsageType.COMPENSATION);
      isCompensated = true;
      // TODO: 출석 보상 알림 생성
    }
    member.setLastLoginAt(LocalDateTime.now(TimeZone.DEFAULT));
    return new MemberCompensation(isCompensated, member);
  }

  @Transactional
  public void compensateMemberForReferralIfValid(Long referrerMemberId, String referralCode) {
    Member referrerMember = memberService.findMemberByMemberIdOrThrow(referrerMemberId);
    Member refereeMember = memberService.findMemberByReferralCodeOrThrow(referralCode);
    validateForReferral(referrerMember, refereeMember);

    gemService.saveChangesOfGemWithMember(referrerMember, REFERRAL_COMPENSATION,
        GemUsageType.COMPENSATION);
    gemService.saveChangesOfGemWithMember(refereeMember, REFERRAL_COMPENSATION,
        GemUsageType.COMPENSATION);

    notificationService.publishReferralNotification(refereeMember, referrerMember);
  }

  private void validateForReferral(Member referrerMember, Member refereeMember) {
    if (referrerMember.getIsRegistrationCompleted()) {
      throw new MemberException(MEMBER_ALREADY_COMPLETED);
    }

    long referrerMemberId = referrerMember.getId();
    long refereeMemberId = refereeMember.getId();
    if (referrerMemberId == refereeMemberId) {
      throw new MemberException(OWN_REFERRAL_CODE);
    }
  }
}
