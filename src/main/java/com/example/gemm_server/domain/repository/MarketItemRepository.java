package com.example.gemm_server.domain.repository;

import com.example.gemm_server.domain.entity.MarketItem;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MarketItemRepository extends JpaRepository<MarketItem, Long> {

  @EntityGraph(attributePaths = {"activity"})
  Optional<MarketItem> findWithActivityById(Long marketItemId);

  @EntityGraph(attributePaths = {"activity", "owner"})
  Optional<MarketItem> findWithActivityAndOwnerById(Long marketItemId);

  @EntityGraph(attributePaths = {"activity", "owner"})
  Page<MarketItem> findWithActivityAndOwnerBy(Pageable pageable);

  @EntityGraph(attributePaths = {"activity", "owner"})
  Page<MarketItem> findWithActivityAndOwnerByOwnerId(Long ownerId, Pageable pageable);

  @EntityGraph(attributePaths = {"activity", "owner"})
  List<MarketItem> findAll(Specification<MarketItem> spec);

  MarketItem findByActivityId(Long activityId);
}