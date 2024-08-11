package com.example.gemm_server.service;

import com.example.gemm_server.domain.entity.Generation;
import com.example.gemm_server.domain.entity.Material;
import com.example.gemm_server.domain.repository.MaterialRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MaterialService {

  private final MaterialRepository materialRepository;
  private final GenerationService generationService;

  public List<Material> getMaterialsWithThumbnailByGenerationId(Long generationId) {
    Generation generation = generationService.getGenerationOrThrow(generationId);
    return materialRepository.findWithThumbnailByActivityIdOrderByIdDesc(
        generation.getActivity().getId());
  }
}
