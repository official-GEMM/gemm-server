package com.example.gemm_server.domain.entity;

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
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id", unique = true, nullable = false)
  private Long id;

  @Column(name = "message", updatable = false, nullable = false)
  private String message;

  @Enumerated(value = EnumType.STRING)
  @Column(name = "event_type", updatable = false, nullable = false)
  private EventType eventType;

  @Column(name = "is_opened", nullable = false)
  @ColumnDefault("0")
  private boolean isOpened;

  @ManyToOne(targetEntity = Member.class, fetch = FetchType.LAZY)
  @JoinColumn(name = "receiver_id", nullable = false)
  private Member receiver;

  @ManyToOne(targetEntity = Member.class, fetch = FetchType.LAZY)
  @JoinColumn(name = "sender_id", nullable = true)
  private Member sender;

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
