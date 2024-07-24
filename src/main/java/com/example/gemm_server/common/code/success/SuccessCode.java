package com.example.gemm_server.common.code.success;

import org.springframework.http.HttpStatus;

public interface SuccessCode {

  HttpStatus getHttpStatus();

  String getMessage();
}