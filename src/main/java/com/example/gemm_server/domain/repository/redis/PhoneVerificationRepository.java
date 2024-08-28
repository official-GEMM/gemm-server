package com.example.gemm_server.domain.repository.redis;

import com.example.gemm_server.domain.entity.redis.PhoneVerification;
import org.springframework.data.repository.CrudRepository;

public interface PhoneVerificationRepository extends
    CrudRepository<PhoneVerification, String> {

}