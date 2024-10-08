package com.example.gemm_server.dto.auth.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
@Schema(description = "출석 보상 응답", requiredProperties = {"isAttendanceCompensated"})
public class AttendanceResponse {

  @Schema(description = "츨석 보상 지급 여부")
  private Boolean isAttendanceCompensated;

  public AttendanceResponse(Boolean isAttendanceCompensated) {
    this.isAttendanceCompensated = isAttendanceCompensated;
  }
}
