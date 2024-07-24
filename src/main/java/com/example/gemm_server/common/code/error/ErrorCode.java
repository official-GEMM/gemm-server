package com.example.gemm_server.common.code.error;

import org.springframework.http.HttpStatus;

public interface ErrorCode {

  HttpStatus getHttpStatus();

  String getMessage();

  RuntimeException getException();

  RuntimeException getException(Throwable cause);
}
