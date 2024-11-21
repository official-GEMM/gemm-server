package com.example.gemm_server.domain.repository;

import com.example.gemm_server.domain.entity.Scrap;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ScrapRepository extends JpaRepository<Scrap, Long> {

  boolean existsByMemberIdAndMarketItemId(Long memberId, Long marketItemId);

  List<Scrap> findByMemberIdAndMarketItemId(Long memberId, Long marketItemId);

  @EntityGraph(attributePaths = {"marketItem"})
  Page<Scrap> findWithMarketItemByMemberId(Long memberId, Pageable pageable);

  Integer countByMarketItemId(Long marketItemId);
}