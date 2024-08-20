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

  FAILED_TO_GENERATE_PPT_THUMBNAIL(HttpStatus.NOT_FOUND, "PPT 썸네일 생성 중 오류가 발생했습니다."),
  FAILED_TO_CONVERT_DOCX_TO_PDF(HttpStatus.NOT_FOUND, "DOCX에서 PDF로 변환 중 오류가 발생했습니다."),
  FAILED_TO_GENERATE_ACTIVITY_SHEET_THUMBNAIL(HttpStatus.NOT_FOUND, "활동지 썸네일 생성 중 오류가 발생했습니다."),

  FAILED_TO_UPLOAD_FILE(HttpStatus.NOT_FOUND, "활동 자료 S3 업로드 중 에러가 발생했습니다."),
  FAILED_TO_DOWNLOAD_FILE(HttpStatus.NOT_FOUND, "S3로부터 활동 자료 다운로드 중 에러가 발생했습니다."),
  FAILED_TO_COPY_FILE(HttpStatus.NOT_FOUND, "임시 파일을 저장소로 옮기던 중 에러가 발생했습니다."),
  FAILED_TO_GENERATE_PRESIGNED_URL(HttpStatus.NOT_FOUND, "파일의 미리 서명된 URL을 생성하던 중 에러가 발생했습니다."),

  NOT_EXIST_MATERIAL(HttpStatus.NOT_FOUND, "임시 자료 파일이 없습니다."),
  NOT_EXIST_THUMBNAIL(HttpStatus.NOT_FOUND, "임시 썸네일이 없습니다."),

  FAILED_TO_CONNECT_TO_LLM_SERVER(HttpStatus.INTERNAL_SERVER_ERROR, "LLM 서버와 통신 중 에러가 발생했습니다."),
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