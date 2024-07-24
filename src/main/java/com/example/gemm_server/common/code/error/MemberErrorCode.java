package com.example.gemm_server.common.code.error;

import com.example.gemm_server.exception.MemberException;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum MemberErrorCode implements ErrorCode {

  MEMBER_DELETED(HttpStatus.BAD_REQUEST, "탈퇴된 사용자입니다."),
  MEMBER_ALREADY_COMPLETED(HttpStatus.BAD_REQUEST, "이미 필수 정보의 제공이 완료된 사용자입니다."),
  OWN_REFERRAL_CODE(HttpStatus.BAD_REQUEST, "자신을 추천인으로 등록할 수 없습니다."),

  LOGIN_NECESSARY(HttpStatus.UNAUTHORIZED, "로그인이 필요한 기능입니다."),
  REFRESH_TOKEN_NECESSARY(HttpStatus.UNAUTHORIZED, "리프레시 토큰이 필요한 기능입니다."),

  MEMBER_BANNED(HttpStatus.FORBIDDEN, "관리자에 의해 사용이 금지된 사용자입니다."),
  LACK_OF_AUTHORITY(HttpStatus.FORBIDDEN, "관리자 권한이 필요한 기능입니다."),

  MEMBER_NOT_FOUND(HttpStatus.NOT_FOUND, "존재하지 않는 사용자 ID 입니다."),
  REFERRAL_NOT_FOUND(HttpStatus.NOT_FOUND, "존재하지 않는 추천인 코드입니다."),

  INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "서버 에러입니다. 서버 팀에 연락주세요!");

  private final HttpStatus httpStatus;
  private final String message;

  MemberErrorCode(HttpStatus httpStatus, String message) {
    this.httpStatus = httpStatus;
    this.message = message;
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