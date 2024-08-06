package com.example.gemm_server.domain.repository;

import com.example.gemm_server.domain.entity.Material;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MaterialRepository extends JpaRepository<Material, Long> {

}
