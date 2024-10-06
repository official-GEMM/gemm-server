package com.example.gemm_server.domain.entity;

import com.example.gemm_server.common.annotation.entity.ColumnDescription;
import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import java.time.LocalDateTime;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Getter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class Timestamped {

  @ColumnDescription("생성 일시")
  @CreatedDate
  @Column(name = "created_at", updatable = false, nullable = false, columnDefinition = "DATETIME(3)")
  protected LocalDateTime createdAt;

  @ColumnDescription("삭제 일시")
  @Column(name = "deleted_at", columnDefinition = "DATETIME(3)")
  protected LocalDateTime deletedAt;
}
