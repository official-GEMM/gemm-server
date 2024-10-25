package com.example.gemm_server.domain.entity.redis;

import com.example.gemm_server.common.constant.Policy;
import java.time.LocalDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

@RedisHash(value = "phoneVerificationSendAttempt", timeToLive = 86400) // 1일 TTL
public class VerificationSmsSendAttempt {

  @Id
  private String id;
  private int attemptCount;

  // serialization 해결을 위한 기본 생성자
  public VerificationSmsSendAttempt() {
    this.attemptCount = 0;
  }

  public VerificationSmsSendAttempt(Long memberId, LocalDate attemptDate) {
    this.id = createCompositeKey(memberId, attemptDate);
    this.attemptCount = 0;
  }

  public int incrementAttemptCount() {
    return ++this.attemptCount;
  }

  public boolean isAttemptCountValid() {
    return attemptCount <= Policy.VERIFICATION_SMS_SEND_LIMIT;
  }

  public static String createCompositeKey(Long memberId, LocalDate date) {
    return memberId + ":" + date;
  }
}
