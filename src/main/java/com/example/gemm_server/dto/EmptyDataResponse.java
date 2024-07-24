package com.example.gemm_server.dto;

import com.example.gemm_server.common.code.success.SuccessCode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;
import org.springframework.http.MediaType;

public class EmptyDataResponse extends CommonResponse<Object> {

  public EmptyDataResponse() {
    super(Collections.emptyMap());
  }

  public EmptyDataResponse(String message) {
    super(Collections.emptyMap(), message);
  }

  public EmptyDataResponse(int code, String message) {
    super(code, Collections.emptyMap(), message);
  }

  public EmptyDataResponse(SuccessCode code) {
    super(code.getHttpStatus().value(), Collections.emptyMap(), code.getMessage());
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
    EmptyDataResponse errorResponse = new EmptyDataResponse(statusCode, message);
    response.getWriter().write(objectMapper.writeValueAsString(errorResponse));
  }
}
