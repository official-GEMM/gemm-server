package com.example.gemm_server.domain.repository;

import com.example.gemm_server.domain.entity.Generation;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GenerationRepository extends JpaRepository<Generation, Long> {

  @EntityGraph(attributePaths = {"activity"})
  Optional<Generation> findWithActivityById(Long generationId);

  @EntityGraph(attributePaths = {"activity"})
  Page<Generation> findWithActivityByOwnerIdAndActivityMaterialTypeOrderByCreatedAtDesc(
      Long ownerId, Short materialType, Pageable pageable);

  @EntityGraph(attributePaths = {"activity"})
  Page<Generation> findWithActivityByOwnerIdAndActivityMaterialTypeNotOrderByCreatedAtDesc(
      Long ownerId, Short materialType, Pageable pageable);
}
