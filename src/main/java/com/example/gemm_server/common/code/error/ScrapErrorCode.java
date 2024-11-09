package com.example.gemm_server.common.code.error;

import com.example.gemm_server.exception.ScrapException;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ScrapErrorCode implements ErrorCode {
  SCRAP_AlREADY_EXISTS(HttpStatus.BAD_REQUEST, "이미 스크랩이 완료된 상품입니다."),
  SCRAP_NOT_FOUND_FOR_USER(HttpStatus.BAD_REQUEST, "사용자의 스크랩이 존재하지 않습니다."),

  SCRAP_NOT_FOUND(HttpStatus.NOT_FOUND, "존재하지 않는 스크랩 ID 입니다."),

  INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "서버 에러입니다. 서버 팀에 연락주세요!");

  private final HttpStatus httpStatus;
  private final String message;

  ScrapErrorCode(HttpStatus httpStatus, String message) {
    this.httpStatus = httpStatus;
    this.message = message;
  }

  @Override
  public ScrapException getException() {
    return new ScrapException(this);
  }

  @Override
  public ScrapException getException(Throwable cause) {
    return new ScrapException(this, cause);
  }
}