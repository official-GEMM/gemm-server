package com.example.gemm_server.common.code.error;

import com.example.gemm_server.exception.GemException;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum GemErrorCode implements ErrorCode {

  GEM_TO_BE_POSITIVE(HttpStatus.BAD_REQUEST, "젬의 양은 양수로 전달되어야 합니다."),
  NOT_ENOUGH_GEM(HttpStatus.BAD_REQUEST, "젬이 부족합니다."),

  INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "서버 에러입니다. 서버 팀에 연락주세요!");

  private final HttpStatus httpStatus;
  private final String message;

  GemErrorCode(HttpStatus httpStatus, String message) {
    this.httpStatus = httpStatus;
    this.message = message;
  }

  @Override
  public GemException getException() {
    return new GemException(this);
  }

  @Override
  public GemException getException(Throwable cause) {
    return new GemException(this, cause);
  }
}