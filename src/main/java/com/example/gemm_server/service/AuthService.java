package com.example.gemm_server.service;

import static com.example.gemm_server.common.constant.Policy.ATTENDANCE_COMPENSATION;

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

  public Member compensateMemberForDailyAttendance(Long memberId) {
    Member member = memberRepository.findOneById(memberId);
    if (!DateUtil.isToday(member.getLastLoginAt())) {
      member.setGem(member.getGem() + ATTENDANCE_COMPENSATION);
    }
    member.setLastLoginAt(LocalDateTime.now());
    return memberRepository.save(member);
  }
}
