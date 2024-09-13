package com.example.gemm_server.exception;

import com.example.gemm_server.common.code.error.DealErrorCode;

public class DealException extends CustomException {

  public DealException() {
    super();
  }

  public DealException(String message) {
    super(message);
  }

  public DealException(String message, Throwable cause) {
    super(message, cause);
  }

  public DealException(DealErrorCode errorCode) {
    super(errorCode);
  }

  public DealException(DealErrorCode errorCode, Throwable cause) {
    super(errorCode, cause);
  }
}
