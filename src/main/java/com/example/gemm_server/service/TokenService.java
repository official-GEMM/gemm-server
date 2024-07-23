package com.example.gemm_server.service;

import com.example.gemm_server.domain.entity.Member;
import com.example.gemm_server.domain.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TokenService {

  private final MemberRepository memberRepository;

  public String getRefreshToken(Long memberId) {
    return memberRepository.findOneById(memberId).getRefreshToken();
  }

  public void updateRefreshToken(Long memberId, String refreshToken) {
    Member member = memberRepository.findOneById(memberId);
    member.setRefreshToken(refreshToken);
    memberRepository.save(member);
  }
}
