package com.example.gemm_server.security.oauth2;

import com.example.gemm_server.domain.entity.Member;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

public record CustomOauth2User(
    Member member,
    Map<String, Object> attributes,
    String attributeKey) implements OAuth2User, UserDetails {

  @Override
  public String getName() {
    return member.getId().toString();
  }

  @Override
  public Map<String, Object> getAttributes() {
    return attributes;
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return Collections.singletonList(
        new SimpleGrantedAuthority(member.getRole().getName()));
  }

  @Override
  public String getPassword() {
    return null;
  }

  @Override
  public String getUsername() {
    return member.getId().toString();
  }

}