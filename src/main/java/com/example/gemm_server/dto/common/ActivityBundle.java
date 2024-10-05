package com.example.gemm_server.dto.common;

import com.example.gemm_server.common.constant.FilePath;
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
      return S3Util.getFileUrl(FilePath.defaultThumbnailImagePath);
    }
    return S3Util.getFileUrl(thumbnail.getDirectoryPath() + thumbnail.getFileName());
  }
}