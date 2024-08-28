package com.example.gemm_server.domain.entity.redis;

import com.example.gemm_server.common.constant.Policy;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

@Getter
@RedisHash(value = "phoneVerificationCode", timeToLive = 300) // 5분
public class PhoneVerification {

  @Id
  private String phoneNumber;
  private String verificationCode;
  private int attemptCount;

  public PhoneVerification(String phoneNumber, String verificationCode) {
    this.phoneNumber = phoneNumber;
    this.verificationCode = verificationCode;
    this.attemptCount = 0;
  }

  public int incrementAttemptCount() {
    return ++this.attemptCount;
  }

  public boolean verify(String verificationCode) {
    return this.verificationCode.equals(verificationCode);
  }

  public boolean isAttemptCountValid() {
    return attemptCount <= Policy.VERIFICATION_LIMIT;
  }
}
