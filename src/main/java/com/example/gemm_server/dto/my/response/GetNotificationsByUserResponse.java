package com.example.gemm_server.dto.my.response;

import com.example.gemm_server.domain.entity.Notification;
import com.example.gemm_server.dto.my.NotificationResponse;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import lombok.Getter;

@Getter
@Schema(description = "내 알림 리스트 응답", requiredProperties = {"notifications"})
public class GetNotificationsByUserResponse {

  @Schema(description = "내 알림 리스트")
  private NotificationResponse[] notifications;

  public GetNotificationsByUserResponse(List<Notification> notifications) {
    this.notifications = notifications.stream().map(NotificationResponse::new)
        .toArray(NotificationResponse[]::new);
  }
}