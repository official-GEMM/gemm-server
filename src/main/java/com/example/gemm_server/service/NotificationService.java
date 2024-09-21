package com.example.gemm_server.service;

import com.example.gemm_server.common.constant.NotificationMessage;
import com.example.gemm_server.common.constant.Policy;
import com.example.gemm_server.common.constant.TimeZone;
import com.example.gemm_server.common.enums.EventType;
import com.example.gemm_server.domain.entity.Member;
import com.example.gemm_server.domain.entity.Notification;
import com.example.gemm_server.domain.repository.NotificationRepository;
import com.example.gemm_server.dto.common.MemberBundle;
import com.example.gemm_server.dto.my.NotificationBundle;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class NotificationService {

  private static final int DAYS_TO_EXPIRATION = 30;
  private final NotificationRepository notificationRepository;
  private final MemberService memberService;

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

  @Transactional
  public List<Notification> getRecentNotificationsByMember(Long memberId) {
    LocalDateTime dateBeforeExpiration = LocalDateTime.now(TimeZone.DEFAULT)
        .minusDays(DAYS_TO_EXPIRATION);
    return notificationRepository.findByReceiverIdAndCreatedAtDateAfterOrderByCreatedAtDesc(
        memberId, dateBeforeExpiration);
  }

  @Transactional
  public List<Notification> markNotificationsAsOpened(List<Notification> notifications) {
    notifications.forEach(notification -> notification.setOpened(true));
    return notifications;
  }

  public int countOfUnopenedNotifications(Long memberId) {
    LocalDateTime dateBeforeExpiration = LocalDateTime.now(TimeZone.DEFAULT)
        .minusDays(DAYS_TO_EXPIRATION);

    return notificationRepository.countByReceiverIdAndOpenedAndCreatedAtDateAfter(memberId, false,
        dateBeforeExpiration);
  }

  public NotificationBundle convertToNotificationBundle(Notification notification) {
    MemberBundle sender = memberService.convertToMemberBundle(notification.getSender());
    return new NotificationBundle(notification, sender);
  }

  public List<NotificationBundle> convertToNotificationBundle(List<Notification> notifications) {
    return notifications.stream().map(this::convertToNotificationBundle).toList();
  }
}
