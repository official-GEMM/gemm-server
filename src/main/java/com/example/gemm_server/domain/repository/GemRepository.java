package com.example.gemm_server.domain.repository;

import com.example.gemm_server.domain.entity.Gem;
import org.springframework.data.jpa.repository.JpaRepository;


public interface GemRepository extends JpaRepository<Gem, Integer> {

}
