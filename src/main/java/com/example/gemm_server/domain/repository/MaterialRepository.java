package com.example.gemm_server.domain.repository;

import com.example.gemm_server.domain.entity.Material;
import java.util.List;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MaterialRepository extends JpaRepository<Material, Long> {

  @EntityGraph(attributePaths = {"thumbnails"})
  List<Material> findWithThumbnailByActivityIdOrderByIdDesc(Long activityId);
}
