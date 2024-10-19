package com.example.gemm_server.service;

import com.example.gemm_server.domain.entity.Material;
import com.example.gemm_server.domain.repository.MaterialRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MaterialService {

  private final MaterialRepository materialRepository;

  public List<Material> getMaterialsWithThumbnailByActivityId(Long activityId) {
    return materialRepository.findWithThumbnailByActivityIdOrderByType(activityId);
  }

  public List<Material> getMaterialsByActivityId(Long activityId) {
    return materialRepository.findByActivityIdOrderByType(activityId);
  }
}
