package com.example.gemm_server.exception;

import com.example.gemm_server.dto.CommonResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.NoHandlerFoundException;

@Controller
@ControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler({CustomException.class})
  protected ResponseEntity<CommonResponse<?>> handleCustomException(CustomException ex) {
    return new ResponseEntity<>(
        new CommonResponse<>(ex.getStatusCode(), null,
            ex.getMessage()),
        ex.getHttpStatus());
  }

  @ExceptionHandler({NoHandlerFoundException.class})
  protected ResponseEntity<CommonResponse<?>> handleNoHandlerFoundException(
      NoHandlerFoundException ex) {
    return new ResponseEntity<>(new CommonResponse<>(HttpStatus.NOT_FOUND.value(), null,
        ex.getMessage()), HttpStatus.NOT_FOUND);
  }

  @ExceptionHandler({Exception.class})
  protected ResponseEntity<CommonResponse<?>> handleServerException(Exception ex) {
    return new ResponseEntity<>(new CommonResponse<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), null,
        ex.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
  }
}