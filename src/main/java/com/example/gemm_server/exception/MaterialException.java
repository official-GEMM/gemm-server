package com.example.gemm_server.exception;

import com.example.gemm_server.common.code.error.MaterialErrorCode;

public class MaterialException extends CustomException {

  public MaterialException() {
    super();
  }

  public MaterialException(String message) {
    super(message);
  }

  public MaterialException(String message, Throwable cause) {
    super(message, cause);
  }

  public MaterialException(MaterialErrorCode errorCode) {
    super(errorCode);
  }

  public MaterialException(MaterialErrorCode errorCode, Throwable cause) {
    super(errorCode, cause);
  }
}
