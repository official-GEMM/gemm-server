package com.example.gemm_server.service;

import static com.example.gemm_server.common.constant.Policy.ATTENDANCE_COMPENSATION;
import static com.example.gemm_server.common.constant.Policy.REFERRAL_COMPENSATION;

import com.example.gemm_server.common.enums.GemUsageType;
import com.example.gemm_server.common.util.DateUtil;
import com.example.gemm_server.domain.entity.Member;
import com.example.gemm_server.domain.entity.redis.TokenBlackList;
import com.example.gemm_server.domain.repository.redis.RefreshTokenRepository;
import com.example.gemm_server.domain.repository.redis.TokenBlackListRepository;
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

  @Transactional
  public void logout(Long memberId, String token) {
    TokenBlackList blackList = new TokenBlackList(token);
    tokenBlackListRepository.save(blackList);

    refreshTokenRepository.deleteById(memberId);
  }

  @Transactional
  public Member compensateMemberForDailyAttendance(Long memberId) {
    Member member = memberService.findMemberByMemberId(memberId);
    if (!DateUtil.isToday(member.getLastLoginAt())) {
      gemService.saveChangesOfGemWithMember(member, ATTENDANCE_COMPENSATION,
          GemUsageType.COMPENSATION);
    }
    member.setLastLoginAt(LocalDateTime.now(TimeZone.DEFAULT));
    return member;
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

  }
}
