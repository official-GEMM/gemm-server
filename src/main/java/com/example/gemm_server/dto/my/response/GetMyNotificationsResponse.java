package com.example.gemm_server.dto.my.response;

import com.example.gemm_server.dto.my.Notifications;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
public class GetMyNotificationsResponse {

  @Schema(description = "내 알림 리스트")
  private Notifications[] notifications;

  public GetMyNotificationsResponse() {
  }
}