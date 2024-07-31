package com.example.gemm_server.service;

import com.example.gemm_server.domain.entity.redis.RefreshToken;
import com.example.gemm_server.domain.repository.redis.RefreshTokenRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TokenService {

  private final RefreshTokenRepository refreshTokenRepository;

  public String getRefreshToken(Long memberId) {
    Optional<RefreshToken> refreshToken = refreshTokenRepository.findById(memberId);
    return refreshToken
        .map(RefreshToken::getRefreshToken)
        .orElse(null);
  }

  public void updateRefreshToken(Long memberId, String refreshToken) {
    RefreshToken redis = new RefreshToken(memberId, refreshToken);
    refreshTokenRepository.save(redis);
  }
}
