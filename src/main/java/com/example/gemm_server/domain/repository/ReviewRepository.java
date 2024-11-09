package com.example.gemm_server.domain.repository;

import com.example.gemm_server.domain.entity.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review, Long> {

  @EntityGraph(attributePaths = {"member"})
  Page<Review> findWithMemberByMarketItemId(Long marketItemId, Pageable pageable);

  boolean existsByMarketItemIdAndMemberId(Long marketItemId, Long memberId);
}