package com.example.gemm_server.common.code.error;

import com.example.gemm_server.exception.TokenException;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum TokenErrorCode implements ErrorCode {

  NO_TOKEN(HttpStatus.BAD_REQUEST, "토큰이 존재하지 않습니다."),
  INVALID_JWT_TOKEN(HttpStatus.BAD_REQUEST, "유효하지 않은 JWT 토큰입니다."),
  UNSUPPORTED_JWT_TOKEN(HttpStatus.BAD_REQUEST, "지원되지 않는 JWT 토큰입니다."),

  INVALID_JWT_SIGNATURE(HttpStatus.UNAUTHORIZED, "잘못된 JWT 서명입니다."),
  UNMATCHED_REFRESH_TOKEN(HttpStatus.UNAUTHORIZED, "refresh 토큰이 일치하지 않습니다."),

  EXPIRED_JWT_TOKEN(HttpStatus.BAD_REQUEST, "만료된 JWT 토큰입니다."),

  INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "서버 에러입니다. 서버 팀에 연락주세요!");

  private final HttpStatus httpStatus;
  private final String message;

  TokenErrorCode(HttpStatus httpStatus, String message) {
    this.httpStatus = httpStatus;
    this.message = message;
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