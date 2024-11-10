package com.example.gemm_server.domain.repository;

import com.example.gemm_server.domain.entity.Thumbnail;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ThumbnailRepository extends JpaRepository<Thumbnail, Long> {

  Optional<Thumbnail> findFirstByMaterialActivityIdOrderByIdDesc(Long activityId);

  List<Thumbnail> findAllByMaterialId(Long materialId);
}
