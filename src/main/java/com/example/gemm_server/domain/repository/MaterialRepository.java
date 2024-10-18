package com.example.gemm_server.domain.repository;

import com.example.gemm_server.domain.entity.Material;
import java.util.List;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface MaterialRepository extends JpaRepository<Material, Long> {

  @EntityGraph(attributePaths = {"thumbnails"})
  @Query("SELECT m FROM Material m WHERE m.activity.id = :activityId ORDER BY " +
      "CASE m.type " +
      "WHEN com.example.gemm_server.common.enums.MaterialType.PPT THEN 1 " +
      "WHEN com.example.gemm_server.common.enums.MaterialType.ACTIVITY_SHEET THEN 2 " +
      "WHEN com.example.gemm_server.common.enums.MaterialType.CUTOUT THEN 3 " +
      "ELSE 4 END")
  List<Material> findWithThumbnailByActivityIdOrderByType(Long activityId);

  @Query("SELECT m FROM Material m WHERE m.activity.id = :activityId ORDER BY " +
      "CASE m.type " +
      "WHEN com.example.gemm_server.common.enums.MaterialType.PPT THEN 1 " +
      "WHEN com.example.gemm_server.common.enums.MaterialType.ACTIVITY_SHEET THEN 2 " +
      "WHEN com.example.gemm_server.common.enums.MaterialType.CUTOUT THEN 3 " +
      "ELSE 4 END")
  List<Material> findByActivityIdOrderByType(Long activityId);
}
