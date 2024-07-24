package com.example.gemm_server.service;

import static com.example.gemm_server.common.code.error.MemberErrorCode.MEMBER_ALREADY_COMPLETED;
import static com.example.gemm_server.common.code.error.MemberErrorCode.OWN_REFERRAL_CODE;
import static com.example.gemm_server.common.code.error.MemberErrorCode.REFERRAL_NOT_FOUND;
import static com.example.gemm_server.common.constant.Policy.ATTENDANCE_COMPENSATION;

import com.example.gemm_server.common.enums.GemUsage;
import com.example.gemm_server.common.util.DateUtil;
import com.example.gemm_server.domain.entity.Member;
import com.example.gemm_server.domain.repository.MemberRepository;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

  private final MemberRepository memberRepository;
  private final GemService gemService;

  public Member compensateMemberForDailyAttendance(Long memberId) {
    Member member = memberRepository.findOneById(memberId);
    if (!DateUtil.isToday(member.getLastLoginAt())) {
      gemService.saveChangesOfGemWithMember(member, ATTENDANCE_COMPENSATION, GemUsage.COMPENSATION);
    }
    member.setLastLoginAt(LocalDateTime.now());
    return memberRepository.save(member);
  }
}
