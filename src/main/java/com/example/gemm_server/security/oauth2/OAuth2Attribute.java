package com.example.gemm_server.security.oauth2;

import com.example.gemm_server.common.enums.Provider;
import com.example.gemm_server.common.enums.Role;
import com.example.gemm_server.common.util.UUIDUtil;
import com.example.gemm_server.domain.entity.Member;
import java.util.Map;
import lombok.AccessLevel;
import lombok.Builder;

@Builder(access = AccessLevel.PRIVATE)
public record OAuth2Attribute(String socialId, Provider provider) {

  public static OAuth2Attribute of(String socialName, Map<String, Object> attributes) {
    if (Provider.KAKAO.isEqual(socialName)) {
      return ofKakao(attributes);
    }
    if (Provider.GOOGLE.isEqual(socialName)) {
      return ofGoogle(attributes);
    }
    if (Provider.NAVER.isEqual(socialName)) {
      return ofNaver(attributes);
    }
    throw new IllegalArgumentException("Invalid social name");
  }

  private static OAuth2Attribute ofGoogle(Map<String, Object> attributes) {
    return OAuth2Attribute.builder()
        .socialId(String.valueOf(attributes.get(Provider.GOOGLE.getAttributeNameOfId())))
        .provider(Provider.GOOGLE)
        .build();
  }

  private static OAuth2Attribute ofKakao(Map<String, Object> attributes) {
    return OAuth2Attribute.builder()
        .socialId(String.valueOf(attributes.get(Provider.KAKAO.getAttributeNameOfId())))
        .provider(Provider.KAKAO)
        .build();
  }

  public static OAuth2Attribute ofNaver(
      Map<String, Object> attributes) {
    @SuppressWarnings("unchecked")
    Map<String, Object> response = (Map<String, Object>) attributes.get("response");

    return OAuth2Attribute.builder()
        .socialId(String.valueOf(response.get(Provider.NAVER.getAttributeNameOfId())))
        .provider(Provider.NAVER)
        .build();
  }

  public Member toEntityOfRegistration() {
    return Member.builder()
        .socialId(socialId)
        .provider(provider)
        .referralCode(UUIDUtil.generateCharacterUUID(8))
        .gem(0)
        .isRegistrationCompleted(false)
        .role(Role.USER)
        .build();
  }
}