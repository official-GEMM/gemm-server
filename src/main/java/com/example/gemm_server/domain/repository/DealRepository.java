package com.example.gemm_server.domain.repository;

import com.example.gemm_server.domain.entity.Deal;
import java.time.LocalDateTime;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DealRepository extends JpaRepository<Deal, Long> {

  @EntityGraph(attributePaths = {"activity"})
  Optional<Deal> findWithActivityById(Long dealId);

  @EntityGraph(attributePaths = {"activity"})
  Page<Deal> findWithActivityByBuyerId(Long buyerId, Pageable pageable);

  @EntityGraph(attributePaths = {"activity"})
  Page<Deal> findWithActivityByBuyerIdAndCreatedAtBetween(Long buyerId, LocalDateTime startDateTime,
      LocalDateTime endDateTime, Pageable pageable);

  @EntityGraph(attributePaths = {"activity", "buyer"})
  Page<Deal> findWithActivityAndBuyerBySellerIdAndCreatedAtBetween(Long sellerId,
      LocalDateTime startDateTime, LocalDateTime endDateTime, Pageable pageable);

  Boolean existsByActivityIdAndBuyerId(Long activityId, Long buyerId);

  Boolean existsByActivityId(Long activityId);
}