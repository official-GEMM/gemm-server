package com.example.gemm_server.exception;

import com.example.gemm_server.common.code.error.TokenErrorCode;

public class TokenException extends CustomException {

  public TokenException() {
    super();
  }

  public TokenException(String message) {
    super(message);
  }

  public TokenException(String message, Throwable cause) {
    super(message, cause);
  }

  public TokenException(TokenErrorCode errorCode) {
    super(errorCode);
  }

  public TokenException(TokenErrorCode errorCode, Throwable cause) {
    super(errorCode, cause);
  }
}
