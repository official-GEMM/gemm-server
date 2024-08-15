package com.example.gemm_server.common.code.error;

import com.example.gemm_server.exception.GeneratorException;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum GeneratorErrorCode implements ErrorCode {
  EMPTY_MATERIAL_GENERATE_REQUEST(HttpStatus.BAD_REQUEST, "최소한 1개 이상의 자료를 생성해야 합니다."),

  EMPTY_GUIDE_RESULT(HttpStatus.NOT_FOUND, "AI 활동 방법 생성 결과가 비어있습니다."),
  EMPTY_PPT_DESIGN_RESULT(HttpStatus.NOT_FOUND, "PPT 설계 AI 생성 결과가 비어있습니다."),
  EMPTY_ACTIVITY_SHEET_DESIGN_RESULT(HttpStatus.NOT_FOUND, "활동지 설계 AI 생성 결과가 비어있습니다."),
  EMPTY_CUTOUT_DESIGN_RESULT(HttpStatus.NOT_FOUND, "컷도안 설계 AI 생성 결과가 비어있습니다."),
  EMPTY_MATERIAL_RESULT(HttpStatus.NOT_FOUND, "AI 활동 자료 생성 결과가 비어있습니다."),
  EMPTY_PPT_RESULT(HttpStatus.NOT_FOUND, "AI PPT 생성 결과가 비어있습니다."),
  EMPTY_ACTIVITY_SHEET_RESULT(HttpStatus.NOT_FOUND, "AI 활동지 생성 결과가 비어있습니다."),
  EMPTY_CUTOUT_RESULT(HttpStatus.NOT_FOUND, "AI 컷도안 생성 결과가 비어있습니다."),

  INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "LLM 서버 에러입니다. 서버 팀에 연락주세요!");

  private final HttpStatus httpStatus;
  private final String message;

  GeneratorErrorCode(HttpStatus httpStatus, String message) {
    this.httpStatus = httpStatus;
    this.message = message;
  }

  @Override
  public GeneratorException getException() {
    return new GeneratorException(this);
  }

  @Override
  public GeneratorException getException(Throwable cause) {
    return new GeneratorException(this, cause);
  }
}