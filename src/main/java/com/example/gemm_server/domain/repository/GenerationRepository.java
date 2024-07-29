package com.example.gemm_server.domain.repository;

import com.example.gemm_server.domain.entity.Generation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GenerationRepository extends JpaRepository<Generation, Long> {
}
