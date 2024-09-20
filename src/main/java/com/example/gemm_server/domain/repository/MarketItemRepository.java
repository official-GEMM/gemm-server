package com.example.gemm_server.domain.repository;

import com.example.gemm_server.domain.entity.MarketItem;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MarketItemRepository extends JpaRepository<MarketItem, Long> {

  @EntityGraph(attributePaths = {"activity", "owner"})
  Page<MarketItem> findWithActivityAndOwnerBy(Pageable pageable);
}