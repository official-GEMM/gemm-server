package com.example.gemm_server.exception;

import com.example.gemm_server.dto.CommonResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Controller
@ControllerAdvice
public class GlobalExceptionHandler {

  private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

  @ExceptionHandler({CustomException.class})
  protected ResponseEntity<CommonResponse> handleCustomException(CustomException ex) {
    return new ResponseEntity<>(
        new CommonResponse(ex.getStatusCode(), null,
            ex.getMessage()),
        ex.getHttpStatus());
  }

  @ExceptionHandler({Exception.class})
  protected ResponseEntity<CommonResponse> handleServerException(Exception ex) {
    log.error(ex.getMessage(), ex);
    return new ResponseEntity<>(new CommonResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), null,
        ex.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
  }
}