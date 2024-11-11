package com.example.gemm_server.domain.repository;

import com.example.gemm_server.domain.entity.Analytics;
import org.springframework.data.jpa.repository.JpaRepository;


public interface AnalyticsRepository extends JpaRepository<Analytics, Integer> {

}
