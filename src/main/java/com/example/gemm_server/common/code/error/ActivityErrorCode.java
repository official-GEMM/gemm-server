package com.example.gemm_server.common.code.error;

import com.example.gemm_server.exception.ActivityException;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ActivityErrorCode implements ErrorCode {

  ACTIVITY_NOT_FOUND(HttpStatus.NOT_FOUND, "존재하지 않는 활동 ID 입니다."),

  INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "서버 에러입니다. 서버 팀에 연락주세요!");

  private final HttpStatus httpStatus;
  private final String message;

  ActivityErrorCode(HttpStatus httpStatus, String message) {
    this.httpStatus = httpStatus;
    this.message = message;
  }

  @Override
  public ActivityException getException() {
    return new ActivityException(this);
  }

  @Override
  public ActivityException getException(Throwable cause) {
    return new ActivityException(this, cause);
  }
}