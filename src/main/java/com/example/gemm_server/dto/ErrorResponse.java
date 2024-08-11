package com.example.gemm_server.dto;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.MediaType;

@Getter
@Setter
public class ErrorResponse {

  @Schema(description = "Http 응답 코드")
  private int code;

  @Schema(description = "응답 메세지")
  private String message;

  public ErrorResponse(int code, String message) {
    this.code = code;
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
    ErrorResponse errorResponse = new ErrorResponse(statusCode, message);
    response.getWriter().write(objectMapper.writeValueAsString(errorResponse));
  }
}
