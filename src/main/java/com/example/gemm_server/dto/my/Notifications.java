package com.example.gemm_server.dto.my;

import com.example.gemm_server.common.enums.EventType;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;
import lombok.Getter;

@Getter
public class Notifications {

  @Schema(description = "알림 아이디")
  private long notificationId;

  @Schema(description = "알림 보낸이의 아이디")
  private String senderNickname;

  @Schema(description = "알림 보낸이의 프로필")
  private String profileImagePath;

  @Schema(description = "알림 메세지")
  private String message;

  @Schema(description = "알림 발생지")
  private long subjectId;

  @Schema(description = "알림 발생 종류")
  private EventType eventType;

  @Schema(description = "알림 생성일")
  private LocalDateTime createdAt;

  public Notifications() {
  }
}
