package com.example.gemm_server.common.code;

import com.example.gemm_server.exception.TokenException;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum TokenErrorCode implements ErrorCode {

  INVALID_TOKEN(HttpStatus.BAD_REQUEST, "유효하지 않은 토큰입니다."),

  INVALID_JWT_SIGNATURE(HttpStatus.UNAUTHORIZED, "만료된 토큰입니다."),

  INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "서버 에러입니다. 서버 팀에 연락주세요!");

  private final HttpStatus httpStatus;
  private final String message;

  TokenErrorCode(HttpStatus httpStatus, String message) {
    this.httpStatus = httpStatus;
    this.message = message;
  }

  @Override
  public String getMessage() {
    return message;
  }

  @Override
  public HttpStatus getHttpStatus() {
    return httpStatus;
  }

  @Override
  public TokenException getException() {
    return new TokenException(this);
  }

  @Override
  public TokenException getException(Throwable cause) {
    return new TokenException(this, cause);
  }
}