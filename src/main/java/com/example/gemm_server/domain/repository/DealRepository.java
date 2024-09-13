package com.example.gemm_server.domain.repository;

import com.example.gemm_server.domain.entity.Deal;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DealRepository extends JpaRepository<Deal, Long> {

}