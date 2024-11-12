package com.example.gemm_server.exception;

import com.example.gemm_server.common.code.error.ActivityErrorCode;

public class ActivityException extends CustomException {

  public ActivityException() {
    super();
  }

  public ActivityException(String message) {
    super(message);
  }

  public ActivityException(String message, Throwable cause) {
    super(message, cause);
  }

  public ActivityException(ActivityErrorCode errorCode) {
    super(errorCode);
  }

  public ActivityException(ActivityErrorCode errorCode, Throwable cause) {
    super(errorCode, cause);
  }
}
