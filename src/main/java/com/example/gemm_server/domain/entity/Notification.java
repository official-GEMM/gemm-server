package com.example.gemm_server.domain.entity;

import com.example.gemm_server.common.annotation.entity.ColumnDescription;
import com.example.gemm_server.common.constant.TimeZone;
import com.example.gemm_server.common.enums.EventType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

@Getter
@Setter
@NoArgsConstructor
@Entity
@SQLDelete(sql = "UPDATE notification SET deleted_at = CURRENT_TIMESTAMP where id = ?")
@SQLRestriction("deleted_at is NULL")
@Table(name = "notification")
public class Notification extends Timestamped {

  @Id
  @ColumnDescription("아이디")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Long id;

  @ColumnDescription("메세지")
  @Column(name = "message", nullable = false, length = 180) // 최대 60자
  private String message;

  @ColumnDescription("알림 종류")
  @Enumerated(value = EnumType.STRING)
  @Column(name = "event_type", nullable = false)
  private EventType eventType;

  @ColumnDescription("열람 여부")
  @Column(name = "is_opened", nullable = false)
  @ColumnDefault("0")
  private boolean isOpened;

  @ColumnDescription("알림 수신자 아이디")
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "receiver_id", nullable = false)
  private Member receiver;

  @ColumnDescription("알림 송신자 아이디")
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "sender_id")
  private Member sender;

  @ColumnDescription("알림 대상의 아이디")
  @Column(name = "subject_id")
  private Long subjectId;

  @Builder
  public Notification(String message, EventType eventType, Member receiver, Member sender,
      Long subjectId) {

    this.message = message;
    this.eventType = eventType;
    this.receiver = receiver;
    this.sender = sender;
    this.subjectId = subjectId;
    this.isOpened = false;
    this.createdAt = LocalDateTime.now(TimeZone.DEFAULT);
  }
}
