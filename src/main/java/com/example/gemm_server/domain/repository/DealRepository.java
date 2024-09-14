package com.example.gemm_server.domain.repository;

import com.example.gemm_server.domain.entity.Deal;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DealRepository extends JpaRepository<Deal, Long> {

  @EntityGraph(attributePaths = {"activity"})
  Page<Deal> findByBuyerIdOrderByCreatedAtDesc(Long buyerId, Pageable pageable);
}