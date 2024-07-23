package com.example.gemm_server.dto;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.MediaType;

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

  public static void setJsonResponse(
      HttpServletResponse response,
      int statusCode,
      String message
  ) throws IOException {
    ObjectMapper objectMapper = new ObjectMapper();
    response.setStatus(statusCode);
    response.setContentType(MediaType.APPLICATION_JSON_VALUE);
    response.setCharacterEncoding("UTF-8");
    CommonResponse<?> errorResponse = new CommonResponse<>(statusCode, Collections.emptyMap(),
        message);
    response.getWriter().write(objectMapper.writeValueAsString(errorResponse));
  }
}
