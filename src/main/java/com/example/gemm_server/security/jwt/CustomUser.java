package com.example.gemm_server.security.jwt;

import java.util.List;
import lombok.Getter;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

public class CustomUser extends User {

  @Getter
  private Long id;

  public CustomUser(String id, String password, List<SimpleGrantedAuthority> authorities) {
    super(id, password, authorities);
    this.id = Long.parseLong(id);
  }

  public static Long getId(CustomUser user) {
    if (user != null) {
      return user.getId();
    }
    return null;
  }
}
