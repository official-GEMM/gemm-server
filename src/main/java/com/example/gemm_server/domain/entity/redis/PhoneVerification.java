package com.example.gemm_server.domain.entity.redis;

import com.example.gemm_server.common.constant.Policy;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

@Getter
@RedisHash(value = "phoneVerificationCode", timeToLive = 180) // 3분
public class PhoneVerification {

  @Id
  private Long memberId;
  private String phoneNumber;
  private String verificationCode;
  private int attemptCount;
  @Setter
  private boolean verified;

  public PhoneVerification(Long memberId, String phoneNumber, String verificationCode) {
    this.memberId = memberId;
    this.phoneNumber = phoneNumber;
    this.verificationCode = verificationCode;
    this.attemptCount = 0;
    this.verified = false;
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
