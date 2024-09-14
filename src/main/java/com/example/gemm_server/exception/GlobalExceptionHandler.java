package com.example.gemm_server.exception;

import com.example.gemm_server.dto.ErrorResponse;
import jakarta.validation.ConstraintViolationException;
import java.util.HashMap;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingRequestCookieException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.NoHandlerFoundException;

@Controller
@ControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler({CustomException.class})
  protected ResponseEntity<ErrorResponse> handleCustomException(CustomException ex) {
    return new ResponseEntity<>(
        new ErrorResponse(ex.getStatusCode(),
            ex.getMessage()),
        ex.getHttpStatus());
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<ErrorResponse> handleDtoValidation(
      final MethodArgumentNotValidException ex) {
    Map<String, String> errors = new HashMap<>();
    ex.getBindingResult().getAllErrors().forEach((error) -> {
      String fieldName = ((FieldError) error).getField();
      String errorMessage = error.getDefaultMessage();
      errors.put(fieldName, errorMessage);
    });
    return new ResponseEntity<>(
        new ErrorResponse(HttpStatus.BAD_REQUEST.value(), errors.toString()),
        HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(ConstraintViolationException.class)
  public ResponseEntity<ErrorResponse> handleConstraintViolationException(
      ConstraintViolationException ex) {
    Map<String, String> errors = new HashMap<>();
    ex.getConstraintViolations().forEach(violation -> {
      String fieldName = violation.getPropertyPath().toString();
      String errorMessage = violation.getMessage();
      errors.put(fieldName, errorMessage);
    });
    return new ResponseEntity<>(
        new ErrorResponse(HttpStatus.BAD_REQUEST.value(), errors.toString()),
        HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler({MissingRequestCookieException.class})
  protected ResponseEntity<ErrorResponse> handleMissingRequestCookieException(
      MissingRequestCookieException ex) {
    return new ResponseEntity<>(
        new ErrorResponse(HttpStatus.BAD_REQUEST.value(),
            ex.getMessage()), HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler({NoHandlerFoundException.class})
  protected ResponseEntity<ErrorResponse> handleNoHandlerFoundException(
      NoHandlerFoundException ex) {
    return new ResponseEntity<>(
        new ErrorResponse(HttpStatus.NOT_FOUND.value(),
            ex.getMessage()), HttpStatus.NOT_FOUND);
  }

  @ExceptionHandler({Exception.class})
  protected ResponseEntity<ErrorResponse> handleServerException(Exception ex) {
    return new ResponseEntity<>(
        new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(),
            ex.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
  }
}