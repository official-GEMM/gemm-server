package com.example.gemm_server.exception;

import com.example.gemm_server.common.code.error.ScrapErrorCode;

public class ScrapException extends CustomException {

  public ScrapException() {
    super();
  }

  public ScrapException(String message) {
    super(message);
  }

  public ScrapException(String message, Throwable cause) {
    super(message, cause);
  }

  public ScrapException(ScrapErrorCode errorCode) {
    super(errorCode);
  }

  public ScrapException(ScrapErrorCode errorCode, Throwable cause) {
    super(errorCode, cause);
  }
}
