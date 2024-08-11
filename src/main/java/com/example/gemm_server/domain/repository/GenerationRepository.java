package com.example.gemm_server.domain.repository;

import com.example.gemm_server.domain.entity.Generation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GenerationRepository extends JpaRepository<Generation, Long> {

  Page<Generation> findByOwnerIdAndActivityMaterialTypeOrderByCreatedAtDesc(
      Long ownerId, Short materialType, Pageable pageable);

  Page<Generation> findByOwnerIdAndActivityMaterialTypeNotOrderByCreatedAtDesc(
      Long ownerId, Short materialType, Pageable pageable);
}
