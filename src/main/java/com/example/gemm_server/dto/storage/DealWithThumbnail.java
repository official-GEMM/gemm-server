package com.example.gemm_server.dto.storage;

import com.example.gemm_server.common.util.S3Util;
import com.example.gemm_server.domain.entity.Deal;
import com.example.gemm_server.domain.entity.Thumbnail;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class DealWithThumbnail {

  private Deal deal;
  private Thumbnail thumbnail;

  public String getThumbnailPath() {
    if (thumbnail == null) {
      return "default"; // TODO: 기본 썸네일 이미지 경로
    }
    return S3Util.getFileUrl(thumbnail.getFilePath() + thumbnail.getFileName());
  }
}
