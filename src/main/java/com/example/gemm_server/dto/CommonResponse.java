package com.example.gemm_server.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
public class CommonResponse {

  private final Integer code;
  private Object data;
  private String message;

  public static ResponseEntity<CommonResponse> okResponseEntity(Object data) {
    return ResponseEntity.ok(new CommonResponse(HttpStatus.OK.value(), data, null));
  }

  public static ResponseEntity<CommonResponse> okResponseEntity(Object data, String message) {
    return ResponseEntity.ok(new CommonResponse(HttpStatus.OK.value(), data, message));
  }

  public static ResponseEntity<CommonResponse> okResponseEntity(Integer code, Object data,
      String message) {
    return ResponseEntity.ok(new CommonResponse(code, data, message));
  }
}
