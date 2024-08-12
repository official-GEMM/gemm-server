package com.example.gemm_server.common.code.success;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum GenerationSuccessCode implements SuccessCode {
  GUIDE_GENERATION_DELETED(HttpStatus.OK, "생성한 활동 방법이 삭제되었습니다."),
  ACTIVITY_GENERATION_DELETED(HttpStatus.OK, "생성한 활동이 삭제되었습니다.");

  private final HttpStatus httpStatus;
  private final String message;

  GenerationSuccessCode(HttpStatus httpStatus, String message) {
    this.httpStatus = httpStatus;
    this.message = message;
  }
}
