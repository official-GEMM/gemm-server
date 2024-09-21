package com.example.gemm_server.service;

import com.example.gemm_server.domain.entity.Thumbnail;
import com.example.gemm_server.domain.repository.ThumbnailRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ThumbnailService {

  private final ThumbnailRepository thumbnailRepository;

  public Thumbnail getMainThumbnail(Long activityId) {
    return getFirstThumbnailPathByActivityId(activityId);
  }

  public Thumbnail getFirstThumbnailPathByActivityId(Long activityId) {

    Optional<Thumbnail> thumbnail =
        thumbnailRepository.findFirstByMaterialActivityIdOrderByIdDesc(activityId);
    return thumbnail.orElse(null);
  }
}
