package com.example.gemm_server.dto.generator.response;

import java.util.Arrays;

public record LlmDesignedMaterialResponse(
    String[] ppt,
    String activitySheet,
    String cutout
) {

  public boolean isEmptyPptDesign() {
    return ppt().length < 1 || Arrays.stream(ppt())
        .anyMatch(design -> design == null || design.isBlank());
  }

  public boolean isEmptyActivitySheetDesign() {
    return activitySheet().isBlank();
  }

  public boolean isEmptyCutoutDesign() {
    return cutout().isBlank();
  }
}
