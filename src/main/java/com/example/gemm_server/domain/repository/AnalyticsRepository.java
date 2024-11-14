package com.example.gemm_server.domain.repository;

import com.example.gemm_server.domain.entity.Analytics;
import com.example.gemm_server.dto.admin.AnalyticsAverage;
import java.time.LocalDateTime;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


public interface AnalyticsRepository extends JpaRepository<Analytics, Long> {

  @Query(
      "SELECT new com.example.gemm_server.dto.admin.AnalyticsAverage("
          + "AVG(m.layoutCompleteness), AVG(m.readability), AVG(m.generationTime)) "
          + "FROM Analytics m"
  )
  AnalyticsAverage findAverageScore();

  @Query(
      "SELECT new com.example.gemm_server.dto.admin.AnalyticsAverage("
          + "AVG(m.layoutCompleteness), AVG(m.readability), AVG(m.generationTime)) "
          + "FROM Analytics m WHERE m.createdAt >= :startDate AND m.createdAt < :endDate"
  )
  AnalyticsAverage findAverageScoreByCreatedAtBetween(@Param("startDate") LocalDateTime startDate,
      @Param("endDate") LocalDateTime endDate);

}
