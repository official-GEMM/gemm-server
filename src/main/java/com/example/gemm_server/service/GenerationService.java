package com.example.gemm_server.service;

import static com.example.gemm_server.common.code.error.GenerationErrorCode.GENERATION_NOT_FOUND;

import com.example.gemm_server.domain.entity.Generation;
import com.example.gemm_server.domain.repository.GenerationRepository;
import com.example.gemm_server.exception.GenerationException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GenerationService {

  private final GenerationRepository generationRepository;

  public Page<Generation> getGenerationsHasNoMaterialByMemberIdAndPage(Long memberId,
      int page, int limit) {
    Pageable pageable = PageRequest.of(page, limit);
    return generationRepository.findByOwnerIdAndActivityMaterialTypeOrderByCreatedAtDesc(memberId,
        (short) 0, pageable);
  }

  public Page<Generation> getGenerationsHasMaterialByMemberIdAndPage(Long memberId,
      int page, int limit) {
    Pageable pageable = PageRequest.of(page, limit);
    return generationRepository.findByOwnerIdAndActivityMaterialTypeNotOrderByCreatedAtDesc(
        memberId,
        (short) 0, pageable);
  }
}
