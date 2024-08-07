package com.example.gemm_server.service;

import com.example.gemm_server.common.constant.NotificationMessage;
import com.example.gemm_server.common.constant.Policy;
import com.example.gemm_server.common.constant.TimeZone;
import com.example.gemm_server.common.enums.EventType;
import com.example.gemm_server.domain.entity.Member;
import com.example.gemm_server.domain.entity.Notification;
import com.example.gemm_server.domain.repository.NotificationRepository;
import com.example.gemm_server.dto.my.NotificationResponse;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class NotificationService {

  private static final int EXPIRATION_DAY = 30;
  private final NotificationRepository notificationRepository;

  public Notification publishReferralNotification(Member receiver, Member sender) {
    String message = NotificationMessage.chargeGemByReferral(Policy.REFERRAL_COMPENSATION);
    Notification notification = Notification
        .builder()
        .eventType(EventType.REFERRAL)
        .message(message)
        .receiver(receiver)
        .sender(sender)
        .build();
    return notificationRepository.save(notification);
  }
}
