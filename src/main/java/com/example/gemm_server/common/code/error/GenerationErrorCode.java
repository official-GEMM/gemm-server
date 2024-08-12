package com.example.gemm_server.common.code.error;

import com.example.gemm_server.exception.GenerationException;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum GenerationErrorCode implements ErrorCode {
  GENERATION_NOT_BELONGS_TO_MEMBER(HttpStatus.UNAUTHORIZED, "사용자의 생성물이 아닙니다."),

  GENERATION_NOT_FOUND(HttpStatus.NOT_FOUND, "존재하지 않는 생성물 ID 입니다."),

  INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "서버 에러입니다. 서버 팀에 연락주세요!");

  private final HttpStatus httpStatus;
  private final String message;

  GenerationErrorCode(HttpStatus httpStatus, String message) {
    this.httpStatus = httpStatus;
    this.message = message;
  }

  @Override
  public GenerationException getException() {
    return new GenerationException(this);
  }

  @Override
  public GenerationException getException(Throwable cause) {
    return new GenerationException(this, cause);
  }
}