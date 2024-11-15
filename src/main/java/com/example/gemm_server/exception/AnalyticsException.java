package com.example.gemm_server.exception;

import com.example.gemm_server.common.code.error.AnalyticsErrorCode;

public class AnalyticsException extends CustomException {

  public AnalyticsException() {
    super();
  }

  public AnalyticsException(String message) {
    super(message);
  }

  public AnalyticsException(String message, Throwable cause) {
    super(message, cause);
  }

  public AnalyticsException(AnalyticsErrorCode errorCode) {
    super(errorCode);
  }

  public AnalyticsException(AnalyticsErrorCode errorCode, Throwable cause) {
    super(errorCode, cause);
  }
}
