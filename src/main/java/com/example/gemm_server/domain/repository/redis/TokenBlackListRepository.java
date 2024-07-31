package com.example.gemm_server.domain.repository.redis;

import com.example.gemm_server.domain.entity.redis.TokenBlackList;
import org.springframework.data.repository.CrudRepository;

public interface TokenBlackListRepository extends CrudRepository<TokenBlackList, String> {

}
