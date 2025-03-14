package com.example.gemm_server.service;

import static com.example.gemm_server.common.code.error.GenerationErrorCode.GENERATION_NOT_FOUND;

import com.example.gemm_server.domain.entity.Generation;
import com.example.gemm_server.domain.entity.Thumbnail;
import com.example.gemm_server.domain.repository.GenerationRepository;
import com.example.gemm_server.dto.storage.GenerationBundle;
import com.example.gemm_server.exception.GenerationException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GenerationService {

  private final GenerationRepository generationRepository;
  private final ThumbnailService thumbnailService;

  public Page<Generation> getGenerationsHasNoMaterialByMemberIdAndPage(Long memberId,
      int page, int limit) {
    Sort sort = Sort.by(Direction.DESC, "createdAt");
    Pageable pageable = PageRequest.of(page, limit, sort);
    return generationRepository.findWithActivityByOwnerIdAndActivityMaterialType(
        memberId, (short) 0, pageable);
  }

  public Page<Generation> getGenerationsHasMaterialByMemberIdAndPage(Long memberId,
      int page, int limit) {
    Sort sort = Sort.by(Direction.DESC, "createdAt");
    Pageable pageable = PageRequest.of(page, limit, sort);
    return generationRepository.findWithActivityByOwnerIdAndActivityMaterialTypeNot(
        memberId, (short) 0, pageable);
  }

  public Generation getGenerationWithActivityOrThrow(Long generationId) {
    return generationRepository.findWithActivityById(generationId)
        .orElseThrow(() -> new GenerationException(GENERATION_NOT_FOUND));
  }

  public void deleteGeneration(Long generationId) {
    generationRepository.deleteById(generationId);
  }

  public GenerationBundle convertToGenerationBundle(Generation generation) {
    Thumbnail thumbnail = thumbnailService.getMainThumbnail(generation.getActivity().getId());
    return new GenerationBundle(generation, thumbnail);
  }

  public List<GenerationBundle> convertToGenerationBundle(List<Generation> generations) {
    return generations.stream().map(this::convertToGenerationBundle).toList();
  }
}
