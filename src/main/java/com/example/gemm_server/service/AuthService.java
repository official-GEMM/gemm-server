package com.example.gemm_server.service;

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
    Member member = memberService.findMemberByMemberId(memberId);
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
  public void compensateMemberForReferral(Long currentMemberId, String referralCode) {
    Member currentMember = memberService.checkReferralCompenstableAndGetMember(currentMemberId,
        referralCode);
    Member referralMember = memberService.getMemberByReferralCode(referralCode);

    gemService.saveChangesOfGemWithMember(currentMember, REFERRAL_COMPENSATION,
        GemUsageType.COMPENSATION);
    gemService.saveChangesOfGemWithMember(referralMember, REFERRAL_COMPENSATION,
        GemUsageType.COMPENSATION);

    notificationService.publishReferralNotification(referralMember,
        currentMember);
  }
}
