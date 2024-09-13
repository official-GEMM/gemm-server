package com.example.gemm_server.domain.repository;

import com.example.gemm_server.domain.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review, Long> {

}