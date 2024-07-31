package com.example.gemm_server.domain.entity.redis;

import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

@Getter
@RedisHash(value = "refreshToken", timeToLive = 604800)
public class RefreshToken {

  @Id
  private Long memberId;
  private String refreshToken;

  public RefreshToken(Long memberId, String refreshToken) {
    this.refreshToken = refreshToken;
    this.memberId = memberId;
  }
}