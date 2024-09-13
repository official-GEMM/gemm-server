package com.example.gemm_server.domain.repository;

import com.example.gemm_server.domain.entity.Scrap;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ScrapRepository extends JpaRepository<Scrap, Long> {

}