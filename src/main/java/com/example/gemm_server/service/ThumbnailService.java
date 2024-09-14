package com.example.gemm_server.service;

import com.example.gemm_server.domain.entity.Deal;
import com.example.gemm_server.domain.entity.Generation;
import com.example.gemm_server.domain.entity.Thumbnail;
import com.example.gemm_server.domain.repository.ThumbnailRepository;
import com.example.gemm_server.dto.storage.DealWithThumbnail;
import com.example.gemm_server.dto.storage.GenerationWithThumbnail;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ThumbnailService {

  private final ThumbnailRepository thumbnailRepository;

  public List<GenerationWithThumbnail> getMainThumbnailForEachGeneration(
      List<Generation> generations) {
    return generations.stream().map(
            generation -> {
              Thumbnail thumbnail = getFirstThumbnailPathByActivityId(generation.getActivity().getId());
              return new GenerationWithThumbnail(generation, thumbnail);
            })
        .toList();
  }

  public List<DealWithThumbnail> getMainThumbnailForEachDeal(
      List<Deal> deals) {
    return deals.stream().map(
            deal -> {
              Thumbnail thumbnail = getFirstThumbnailPathByActivityId(deal.getActivity().getId());
              return new DealWithThumbnail(deal, thumbnail);
            })
        .toList();
  }

  public Thumbnail getFirstThumbnailPathByActivityId(Long activityId) {

    Optional<Thumbnail> thumbnail =
        thumbnailRepository.findFirstByMaterialActivityIdOrderByIdDesc(activityId);
    return thumbnail.orElse(null);
  }
}
