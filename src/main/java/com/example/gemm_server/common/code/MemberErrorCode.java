package com.example.gemm_server.common.code;

import com.example.gemm_server.exception.MemberException;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum MemberErrorCode implements ErrorCode {

  MEMBER_DELETED(HttpStatus.BAD_REQUEST, "탈퇴된 사용자입니다."),

  MEMBER_BANNED(HttpStatus.FORBIDDEN, "관리자에 의해 사용이 금지된 사용자입니다."),

  MEMBER_NOT_FOUND(HttpStatus.NOT_FOUND, "존재하지 않는 사용자 ID 입니다."),

  INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "서버 에러입니다. 서버 팀에 연락주세요!");

  private final HttpStatus httpStatus;
  private final String message;

  MemberErrorCode(HttpStatus httpStatus, String message) {
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
  public MemberException getException() {
    return new MemberException(this);
  }

  @Override
  public MemberException getException(Throwable cause) {
    return new MemberException(this, cause);
  }
}