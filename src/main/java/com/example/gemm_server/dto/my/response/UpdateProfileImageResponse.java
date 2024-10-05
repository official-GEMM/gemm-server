package com.example.gemm_server.dto.my.response;

import com.example.gemm_server.common.util.S3Util;
import com.example.gemm_server.domain.entity.ProfileImage;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
@Schema(description = "프로필 이미지 변경 응답", requiredProperties = {"profileImagePath"})
public class UpdateProfileImageResponse {

  @Schema(description = "프로필 이미지")
  private String profileImagePath;

  public UpdateProfileImageResponse(ProfileImage profileImage) {
    this.profileImagePath = S3Util.getFileUrl(
        profileImage.getDirectoryPath() + profileImage.getFileName());

  }
}
