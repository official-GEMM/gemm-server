package com.example.gemm_server.common.code.success;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum MemberSuccessCode implements SuccessCode {
  MEMBER_UPDATED(HttpStatus.OK, "사용자 정보가 업데이트되었습니다.");

  private final HttpStatus httpStatus;
  private final String message;

  MemberSuccessCode(HttpStatus httpStatus, String message) {
    this.httpStatus = httpStatus;
    this.message = message;
  }
}
