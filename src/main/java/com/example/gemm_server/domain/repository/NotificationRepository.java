package com.example.gemm_server.domain.repository;

import com.example.gemm_server.domain.entity.Notification;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface NotificationRepository extends JpaRepository<Notification, Long> {

  @EntityGraph(attributePaths = {"sender"})
  @Query("SELECT e FROM Notification e WHERE e.createdAt >= :dateThreshold AND e.receiver.id = :memberId ORDER BY e.createdAt DESC")
  List<Notification> findByReceiverIdAndCreatedAtDateAfter(
      @Param("memberId") Long memberId,
      @Param("dateThreshold") LocalDateTime dateThreshold);

  @Query("SELECT COUNT(e) FROM Notification e WHERE e.createdAt >= :dateThreshold AND e.isOpened = :isOpened AND e.receiver.id = :memberId")
  Integer countByReceiverIdAndOpenedAndCreatedAtDateAfter(
      @Param("memberId") Long memberId,
      @Param("isOpened") boolean isOpened,
      @Param("dateThreshold") LocalDateTime dateThreshold);
}
