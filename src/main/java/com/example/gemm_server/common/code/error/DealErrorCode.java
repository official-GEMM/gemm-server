package com.example.gemm_server.common.code.error;

import com.example.gemm_server.exception.DealException;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum DealErrorCode implements ErrorCode {
  DEAL_NOT_BELONGS_TO_MEMBER(HttpStatus.UNAUTHORIZED, "사용자의 거래가 아닙니다."),

  DEAL_NOT_FOUND(HttpStatus.NOT_FOUND, "존재하지 않는 거래 ID 입니다."),

  INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "서버 에러입니다. 서버 팀에 연락주세요!");

  private final HttpStatus httpStatus;
  private final String message;

  DealErrorCode(HttpStatus httpStatus, String message) {
    this.httpStatus = httpStatus;
    this.message = message;
  }

  @Override
  public DealException getException() {
    return new DealException(this);
  }

  @Override
  public DealException getException(Throwable cause) {
    return new DealException(this, cause);
  }
}