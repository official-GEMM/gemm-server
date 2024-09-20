package com.example.gemm_server.dto.common;

import com.example.gemm_server.common.util.S3Util;
import com.example.gemm_server.domain.entity.Activity;
import com.example.gemm_server.domain.entity.Thumbnail;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ActivityBundle {

  private Activity activity;
  private Thumbnail thumbnail;

  public String getThumbnailPath() {
    if (thumbnail == null) {
      return "default"; // TODO: 기본 썸네일 이미지 경로
    }
    return S3Util.getFileUrl(thumbnail.getFilePath() + thumbnail.getFileName());
  }
}