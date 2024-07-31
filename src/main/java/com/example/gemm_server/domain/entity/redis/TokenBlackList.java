package com.example.gemm_server.domain.entity.redis;

import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

@Getter
@RedisHash(value = "tokenBlackList")
public class TokenBlackList {

  @Id
  private String token;

  public TokenBlackList(String token) {
    this.token = token;
  }
}