package com.example.gemm_server.exception;

import com.example.gemm_server.common.code.ErrorCode;
import org.springframework.http.HttpStatus;

public class CustomException extends RuntimeException {

  private final ErrorCode errorCode;

  private static ErrorCode getDefaultErrorCode() {
    return DefaultErrorCodeHolder.DEFAULT_ERROR_CODE;
  }

  private static class DefaultErrorCodeHolder {

    private static final ErrorCode DEFAULT_ERROR_CODE = new ErrorCode() {

      @Override
      public HttpStatus getHttpStatus() {
        return HttpStatus.INTERNAL_SERVER_ERROR;
      }

      @Override
      public String getMessage() {
        return "서버 오류";
      }

      @Override
      public RuntimeException getException() {
        return new CustomException("SERVER_ERROR");
      }

      @Override
      public RuntimeException getException(Throwable cause) {
        return new CustomException("SERVER_ERROR", cause);
      }
    };
  }

  public CustomException() {
    this.errorCode = getDefaultErrorCode();
  }

  public CustomException(String message) {
    super(message);
    this.errorCode = getDefaultErrorCode();
  }

  public CustomException(String message, Throwable cause) {
    super(message, cause);
    this.errorCode = getDefaultErrorCode();
  }

  public CustomException(ErrorCode errorCode) {
    super(errorCode.getMessage());
    this.errorCode = errorCode;
  }

  public CustomException(ErrorCode errorCode, Throwable cause) {
    super(errorCode.getMessage(), cause);
    this.errorCode = errorCode;
  }

  public HttpStatus getHttpStatus() {
    return errorCode.getHttpStatus();
  }

  public int getStatusCode() {
    return errorCode.getHttpStatus().value();
  }
}