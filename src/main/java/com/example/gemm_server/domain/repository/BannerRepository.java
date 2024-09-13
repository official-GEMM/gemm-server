package com.example.gemm_server.domain.repository;

import com.example.gemm_server.domain.entity.Banner;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BannerRepository extends JpaRepository<Banner, Long> {

}