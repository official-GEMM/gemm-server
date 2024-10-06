package com.example.gemm_server.dto.common.response;

import com.example.gemm_server.common.enums.MaterialType;
import com.example.gemm_server.common.util.S3Util;
import com.example.gemm_server.domain.entity.Material;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
@Schema(description = "활동 자료 응답", requiredProperties = {"materialId", "type", "thumbnailPaths"})
public class MaterialResponse {

  @Schema(description = "자료 아이디")
  private long materialId;

  @Schema(description = "자료 종류")
  private MaterialType type;

  @Schema(description = "썸네일 경로 리스트")
  private String[] thumbnailPaths;

  public MaterialResponse(Material material) {
    this.materialId = material.getId();
    this.type = material.getType();
    this.thumbnailPaths = material.getThumbnails().stream().map((thumbnail) ->
            S3Util.getFileUrl(thumbnail.getDirectoryPath() + thumbnail.getFileName())
        )
        .toArray(String[]::new);
  }
}