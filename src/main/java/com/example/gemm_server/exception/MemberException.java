package com.example.gemm_server.exception;

import com.example.gemm_server.common.code.error.MemberErrorCode;

public class MemberException extends CustomException {

  public MemberException() {
    super();
  }

  public MemberException(String message) {
    super(message);
  }

  public MemberException(String message, Throwable cause) {
    super(message, cause);
  }

  public MemberException(MemberErrorCode errorCode) {
    super(errorCode);
  }

  public MemberException(MemberErrorCode errorCode, Throwable cause) {
    super(errorCode, cause);
  }
}
