package com.example.gemm_server.dto.common.response;

import com.example.gemm_server.common.util.S3Util;
import com.example.gemm_server.domain.entity.Material;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import lombok.Getter;

@Getter
@Schema(description = "활동 자료 다운로드 응답", requiredProperties = {"materialPaths"})
public class DownloadMaterialResponse {

  @Schema(description = "자료 다운로드 경로 리스트")
  private String[] materialPaths;

  public DownloadMaterialResponse(List<Material> materials) {
    materialPaths = materials.stream()
        .map(material -> S3Util.downloadFile(material.getDirectoryPath() + material.getFileName()))
        .toArray(String[]::new);
  }
}
