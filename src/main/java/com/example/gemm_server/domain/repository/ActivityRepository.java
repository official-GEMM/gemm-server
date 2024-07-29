package com.example.gemm_server.domain.repository;

import com.example.gemm_server.domain.entity.Activity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ActivityRepository extends JpaRepository<Activity, Long> {
}
