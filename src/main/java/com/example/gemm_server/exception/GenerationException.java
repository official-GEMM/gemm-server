package com.example.gemm_server.exception;

import com.example.gemm_server.common.code.error.GenerationErrorCode;

public class GenerationException extends CustomException {

  public GenerationException() {
    super();
  }

  public GenerationException(String message) {
    super(message);
  }

  public GenerationException(String message, Throwable cause) {
    super(message, cause);
  }

  public GenerationException(GenerationErrorCode errorCode) {
    super(errorCode);
  }

  public GenerationException(GenerationErrorCode errorCode, Throwable cause) {
    super(errorCode, cause);
  }
}
