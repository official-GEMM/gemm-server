package com.example.gemm_server.domain.repository;

import com.example.gemm_server.domain.entity.MarketItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MarketItemRepository extends JpaRepository<MarketItem, Long> {

}