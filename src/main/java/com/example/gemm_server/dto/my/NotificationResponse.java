package com.example.gemm_server.dto.my;

import com.example.gemm_server.common.enums.EventType;
import com.example.gemm_server.domain.entity.Member;
import com.example.gemm_server.domain.entity.Notification;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;
import lombok.Getter;

@Getter
@Schema(description = "알림 응답", requiredProperties = {"notificationId", "senderNickname",
    "profileImagePath", "message", "subjectId", "eventType", "createdAt"})
public class NotificationResponse {

  @Schema(description = "알림 아이디")
  private long notificationId;

  @Schema(description = "알림 보낸이의 아이디")
  private String senderNickname;

  @Schema(description = "알림 보낸이의 프로필")
  private String profileImagePath;

  @Schema(description = "알림 메세지")
  private String message;

  @Schema(description = "알림 발생지", nullable = true)
  private Long subjectId;

  @Schema(description = "알림 발생 종류")
  private EventType eventType;

  @Schema(description = "알림 생성일")
  private LocalDateTime createdAt;

  public NotificationResponse(Notification notification) {
    Member sender = notification.getSender();
    this.notificationId = notification.getId();
    this.senderNickname = sender.getNickname();
    this.profileImagePath = sender.getProfileImageUrl();
    this.message = notification.getMessage();
    this.eventType = notification.getEventType();
    this.createdAt = notification.getCreatedAt();
    this.subjectId = notification.getSubjectId();
  }
}
