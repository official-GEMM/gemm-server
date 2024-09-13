package com.example.gemm_server.exception;

import com.example.gemm_server.common.code.error.MarketItemErrorCode;

public class MarketItemException extends CustomException {

  public MarketItemException() {
    super();
  }

  public MarketItemException(String message) {
    super(message);
  }

  public MarketItemException(String message, Throwable cause) {
    super(message, cause);
  }

  public MarketItemException(MarketItemErrorCode errorCode) {
    super(errorCode);
  }

  public MarketItemException(MarketItemErrorCode errorCode, Throwable cause) {
    super(errorCode, cause);
  }
}
