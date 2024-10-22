package com.example.gemm_server.domain.repository.redis;

import com.example.gemm_server.domain.entity.redis.VerificationSmsSendAttempt;
import java.time.LocalDate;
import java.util.Optional;
import org.springframework.data.repository.CrudRepository;

public interface VerificationSmsSendAttemptRepository extends
    CrudRepository<VerificationSmsSendAttempt, String> {

  default Optional<VerificationSmsSendAttempt> findByMemberIdAndAttemptDate(Long memberId,
      LocalDate attemptDate) {
    String compositeId = VerificationSmsSendAttempt.createCompositeKey(memberId, attemptDate);
    return findById(compositeId);
  }
}