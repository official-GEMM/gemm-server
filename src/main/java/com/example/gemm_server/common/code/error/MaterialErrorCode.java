package com.example.gemm_server.common.code.error;

import com.example.gemm_server.exception.MaterialException;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum MaterialErrorCode implements ErrorCode {

  MATERIAL_NOT_BELONGS_TO_ACTIVITY(HttpStatus.NOT_FOUND, "해당하는 활동에 속한 자료가 아닙니다."),

  MATERIAL_NOT_FOUND(HttpStatus.NOT_FOUND, "존재하지 않는 활동 ID 입니다."),

  INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "서버 에러입니다. 서버 팀에 연락주세요!");

  private final HttpStatus httpStatus;
  private final String message;

  MaterialErrorCode(HttpStatus httpStatus, String message) {
    this.httpStatus = httpStatus;
    this.message = message;
  }

  @Override
  public MaterialException getException() {
    return new MaterialException(this);
  }

  @Override
  public MaterialException getException(Throwable cause) {
    return new MaterialException(this, cause);
  }
}