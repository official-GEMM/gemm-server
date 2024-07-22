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

  public static void setJsonResponse(
      HttpServletResponse response,
      int statusCode,
      String message
  ) throws IOException {
    ObjectMapper objectMapper = new ObjectMapper();
    response.setStatus(statusCode);
    response.setContentType(MediaType.APPLICATION_JSON_VALUE);
    response.setCharacterEncoding("UTF-8");
    CommonResponse<?> errorResponse = new CommonResponse<>(statusCode, null, message);
    response.getWriter().write(objectMapper.writeValueAsString(errorResponse));
  }
}
