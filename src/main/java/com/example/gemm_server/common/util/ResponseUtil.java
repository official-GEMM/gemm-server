package com.example.gemm_server.common.util;

import com.example.gemm_server.dto.CommonResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.springframework.http.MediaType;

public class ResponseUtil {

  public static void setJsonResponse(
      HttpServletResponse response,
      int statusCode,
      String message
  ) throws IOException {
    ObjectMapper objectMapper = new ObjectMapper();
    response.setStatus(statusCode);
    response.setContentType(MediaType.APPLICATION_JSON_VALUE);
    response.setCharacterEncoding("UTF-8");
    CommonResponse errorResponse = new CommonResponse(statusCode, null, message);
    response.getWriter().write(objectMapper.writeValueAsString(errorResponse));
  }
}
