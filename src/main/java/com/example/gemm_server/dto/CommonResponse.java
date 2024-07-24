package com.example.gemm_server.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommonResponse<T> {

  @Schema(description = "Http 응답 코드")
  private int code;

  @Schema(description = "응답 데이터")
  private T data;

  @Schema(description = "응답 메세지")
  private String message;

  public CommonResponse(T data) {
    this.code = 200;
    this.data = data;
    this.message = "";
  }

  public CommonResponse(T data, String message) {
    this.code = 200;
    this.data = data;
    this.message = message;
  }

  public CommonResponse(int code, T data, String message) {
    this.code = code;
    this.data = data;
    this.message = message;
  }
}
