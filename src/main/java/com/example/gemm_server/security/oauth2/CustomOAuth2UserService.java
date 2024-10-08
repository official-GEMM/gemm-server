package com.example.gemm_server.security.oauth2;

import static com.example.gemm_server.common.code.error.MemberErrorCode.MEMBER_BANNED;
import static com.example.gemm_server.common.code.error.MemberErrorCode.MEMBER_DELETED;
import static com.example.gemm_server.common.constant.Policy.JOIN_COMPENSATION;

import com.example.gemm_server.common.enums.GemUsageType;
import com.example.gemm_server.domain.entity.Member;
import com.example.gemm_server.domain.repository.MemberRepository;
import com.example.gemm_server.service.GemService;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.OAuth2Error;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@RequiredArgsConstructor
@Service
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

  private final MemberRepository memberRepository;
  private final GemService gemService;

  @Transactional
  @Override
  public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {

    Map<String, Object> oAuth2UserAttributes = super.loadUser(userRequest).getAttributes();
    String registrationId = userRequest.getClientRegistration().getRegistrationId();
    OAuth2Attribute oAuth2Attribute = OAuth2Attribute.of(registrationId, oAuth2UserAttributes);

    Member member = getOrCreate(oAuth2Attribute);
    if (isNotYetJoined(member)) {
      Member joinedMember = saveMemberWithJoinCompensation(member);
      return new CustomOauth2User(joinedMember, true, oAuth2UserAttributes);
    }
    return new CustomOauth2User(member, false, oAuth2UserAttributes);
  }

  private Member getOrCreate(OAuth2Attribute oAuth2Attribute) {
    Member member = memberRepository.findOneBySocialIdAndProviderIncludingDeleted(
            oAuth2Attribute.socialId(),
            oAuth2Attribute.provider().toString())
        .orElseGet(oAuth2Attribute::toEntityOfRegistration);
    validateMember(member);
    return member;
  }

  private Member saveMemberWithJoinCompensation(Member notYetJoinedMember) {
    Member joinedMember = memberRepository.save(notYetJoinedMember);
    gemService.saveChangesOfGemWithMember(joinedMember, JOIN_COMPENSATION,
        GemUsageType.COMPENSATION);
    return joinedMember;
  }

  private void validateMember(Member member) {
    if (member.getDeletedAt() != null) {
      throw new OAuth2AuthenticationException(new OAuth2Error("UNAUTHORIZED"),
          MEMBER_DELETED.getMessage());
    }
    if (member.getBannedAt() != null) {
      throw new OAuth2AuthenticationException(new OAuth2Error("UNAUTHORIZED"),
          MEMBER_BANNED.getMessage());
    }
  }

  private boolean isNotYetJoined(Member member) {
    return member.getId() == null;
  }
}