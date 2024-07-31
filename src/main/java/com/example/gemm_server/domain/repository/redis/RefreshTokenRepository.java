package com.example.gemm_server.domain.repository.redis;

import com.example.gemm_server.domain.entity.redis.RefreshToken;
import org.springframework.data.repository.CrudRepository;

public interface RefreshTokenRepository extends CrudRepository<RefreshToken, Long> {

}