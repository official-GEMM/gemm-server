package com.example.gemm_server.domain.repository;

import com.example.gemm_server.domain.entity.Deal;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DealRepository extends JpaRepository<Deal, Long> {

  @EntityGraph(attributePaths = {"activity"})
  Optional<Deal> findWithActivityById(Long dealId);

  @EntityGraph(attributePaths = {"activity"})
  Page<Deal> findWithActivityByBuyerIdOrderByCreatedAtDesc(Long buyerId, Pageable pageable);
}