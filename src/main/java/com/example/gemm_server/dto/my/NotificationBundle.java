package com.example.gemm_server.dto.my;

import com.example.gemm_server.domain.entity.Notification;
import com.example.gemm_server.dto.common.MemberBundle;
import lombok.Getter;

@Getter
public class NotificationBundle {

  private Notification notification;
  private MemberBundle sender;

  public NotificationBundle(Notification notification, MemberBundle sender) {
    this.notification = notification;
    this.sender = sender;
  }

}
