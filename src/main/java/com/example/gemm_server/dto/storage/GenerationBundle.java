package com.example.gemm_server.dto.storage;

import com.example.gemm_server.domain.entity.Generation;
import com.example.gemm_server.domain.entity.Thumbnail;
import com.example.gemm_server.dto.common.ActivityBundle;
import lombok.Getter;

@Getter
public class GenerationBundle extends ActivityBundle {

  private Generation generation;

  public GenerationBundle(Generation generationWithActivity, Thumbnail thumbnail) {
    super(generationWithActivity.getActivity(), thumbnail);
    this.generation = generationWithActivity;
  }
}
