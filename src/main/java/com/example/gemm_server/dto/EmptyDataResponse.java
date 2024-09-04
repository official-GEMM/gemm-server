package com.example.gemm_server.dto;

import com.example.gemm_server.common.code.success.SuccessCode;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.Collections;

@Schema(description = "빈 데이터 공통 응답", requiredProperties = {"code", "message"})
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
}
