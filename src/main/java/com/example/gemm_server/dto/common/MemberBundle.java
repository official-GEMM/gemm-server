package com.example.gemm_server.dto.common;

import com.example.gemm_server.common.constant.FilePath;
import com.example.gemm_server.common.util.S3Util;
import com.example.gemm_server.domain.entity.Member;
import com.example.gemm_server.domain.entity.ProfileImage;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MemberBundle {

  private Member member;
  private ProfileImage profileImage;

  public String getProfileImageUrl() {
    if (profileImage == null) {
      return S3Util.getFileUrl(FilePath.defaultProfileImagePath);
    }
    return S3Util.getFileUrl(profileImage.getFilePath() + profileImage.getFileName());
  }
}