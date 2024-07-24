package com.example.gemm_server.exception;

import com.example.gemm_server.dto.EmptyDataResponse;
import java.util.HashMap;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.NoHandlerFoundException;

@Controller
@ControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler({CustomException.class})
  protected ResponseEntity<EmptyDataResponse> handleCustomException(CustomException ex) {
    return new ResponseEntity<>(
        new EmptyDataResponse(ex.getStatusCode(),
            ex.getMessage()),
        ex.getHttpStatus());
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<EmptyDataResponse> handleDtoValidation(
      final MethodArgumentNotValidException ex) {
    Map<String, String> errors = new HashMap<>();
    ex.getBindingResult().getAllErrors().forEach((error) -> {
      String fieldName = ((FieldError) error).getField();
      String errorMessage = error.getDefaultMessage();
      errors.put(fieldName, errorMessage);
    });
    return new ResponseEntity<>(
        new EmptyDataResponse(HttpStatus.BAD_REQUEST.value(), errors.toString()),
        HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler({NoHandlerFoundException.class})
  protected ResponseEntity<EmptyDataResponse> handleNoHandlerFoundException(
      NoHandlerFoundException ex) {
    return new ResponseEntity<>(
        new EmptyDataResponse(HttpStatus.NOT_FOUND.value(),
            ex.getMessage()), HttpStatus.NOT_FOUND);
  }

  @ExceptionHandler({Exception.class})
  protected ResponseEntity<EmptyDataResponse> handleServerException(Exception ex) {
    return new ResponseEntity<>(
        new EmptyDataResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(),
            ex.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
  }
}