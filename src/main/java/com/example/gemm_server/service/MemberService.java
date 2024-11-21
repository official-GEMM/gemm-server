package com.example.gemm_server.service;

import static com.example.gemm_server.common.code.error.MemberErrorCode.MEMBER_NOT_FOUND;
import static com.example.gemm_server.common.code.error.MemberErrorCode.NICKNAME_DUPLICATED;
import static com.example.gemm_server.common.code.error.MemberErrorCode.REFERRAL_NOT_FOUND;

import com.example.gemm_server.common.util.DateUtil;
import com.example.gemm_server.domain.entity.Member;
import com.example.gemm_server.domain.entity.ProfileImage;
import com.example.gemm_server.domain.repository.MarketItemRepository;
import com.example.gemm_server.domain.repository.MemberRepository;
import com.example.gemm_server.domain.repository.ProfileImageRepository;
import com.example.gemm_server.dto.auth.request.PostNecessaryMemberDataRequest;
import com.example.gemm_server.dto.common.MemberBundle;
import com.example.gemm_server.dto.my.request.UpdateMyInformationRequest;
import com.example.gemm_server.exception.MemberException;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MemberService {

  private final MemberRepository memberRepository;
  private final ProfileImageRepository profileImageRepository;
  private final MarketItemRepository marketItemRepository;

  public Member findMemberByMemberIdOrThrow(Long memberId) {
    return memberRepository.findOneById(memberId)
        .orElseThrow(() -> new MemberException(MEMBER_NOT_FOUND));
  }

  public Member findMemberByReferralCodeOrThrow(String referralCode) {
    return memberRepository.findOneByReferralCode(referralCode)
        .orElseThrow(() -> new MemberException(REFERRAL_NOT_FOUND));
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
    member.setIsRegistrationCompleted(true);
    return member;
  }

  public void validateNicknameForUpdate(Long memberId, String nickname) {
    if (memberRepository.existsByIdNotAndNickname(memberId, nickname)) {
      throw new MemberException(NICKNAME_DUPLICATED);
    }
  }

  public boolean isNicknameExists(String nickname) {
    return memberRepository.existsByNickname(nickname);
  }

  public boolean isPhoneNumberDuplicated(Long memberId, String phoneNumber) {
    Optional<Member> member = this.memberRepository.findOneByPhoneNumber(phoneNumber);
    return member.isPresent() && !member.get().getId().equals(memberId);
  }

  @Transactional
  public Member updateMyInformation(Long memberId, UpdateMyInformationRequest memberInfo) {
    Member member = findMemberByMemberIdOrThrow(memberId);
    member.setManageAge(memberInfo.getManageAge());
    member.setPhoneNumber(memberInfo.getPhoneNumber());
    member.setNickname(memberInfo.getNickname());
    member.setBirth(memberInfo.getBirth());
    return member;
  }

  public void withdrawMember(Long memberId) {
    marketItemRepository.deleteByOwnerId(memberId);
    deleteMember(memberId);
  }

  public void deleteMember(Long memberId) {
    memberRepository.deleteById(memberId);
  }

  public MemberBundle convertToMemberBundle(Member member) {
    ProfileImage profileImage = profileImageRepository.findByMemberId(member.getId());
    return new MemberBundle(member, profileImage);
  }
}