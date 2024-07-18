package com.example.gemm_server.security.oauth2;

import com.example.gemm_server.common.Provider;
import com.example.gemm_server.common.util.DateUtil;
import com.example.gemm_server.domain.entity.Member;
import java.time.LocalDate;
import java.util.Map;
import lombok.AccessLevel;
import lombok.Builder;

@Builder(access = AccessLevel.PRIVATE)
public record OAuth2Attribute(String name, String socialId, Provider provider, String gender,
                              LocalDate birth) {

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
        .name(String.valueOf(attributes.get("name")))
        .socialId(String.valueOf(attributes.get(Provider.GOOGLE.getAttributeNameOfId())))
        .provider(Provider.GOOGLE)
        .build();
  }

  private static OAuth2Attribute ofKakao(Map<String, Object> attributes) {
    @SuppressWarnings("unchecked")
    Map<String, Object> kakaoAccount = (Map<String, Object>) attributes.get("kakao_account");
    @SuppressWarnings("unchecked")
    Map<String, Object> kakaoProfile = (Map<String, Object>) kakaoAccount.get("profile");

    return OAuth2Attribute.builder()
        .name(String.valueOf(kakaoProfile.get("nickname")))
        .socialId(String.valueOf(attributes.get(Provider.KAKAO.getAttributeNameOfId())))
        .provider(Provider.KAKAO)
        .build();
  }

  public static OAuth2Attribute ofNaver(
      Map<String, Object> attributes) {
    @SuppressWarnings("unchecked")
    Map<String, Object> response = (Map<String, Object>) attributes.get("response");
    String birth = response.get("birthyear") + "-" + response.get("birthday");

    return OAuth2Attribute.builder()
        .name(String.valueOf(response.get("name")))
        .socialId(String.valueOf(response.get(Provider.NAVER.getAttributeNameOfId())))
        .provider(Provider.NAVER)
        .gender((String) response.get("gender"))
        .birth(DateUtil.parseYearMonthDay(birth))
        .build();
  }

  public Member toEntity() {
    return Member.createForSignUp(name, socialId, provider, birth);
  }
}