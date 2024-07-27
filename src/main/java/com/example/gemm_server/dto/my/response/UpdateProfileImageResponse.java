package com.example.gemm_server.dto.my.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import org.springframework.core.io.UrlResource;

@Getter
public class UpdateProfileImageResponse {

  @Schema(description = "프로필 이미지")
  private UrlResource profileImagePath;

  public UpdateProfileImageResponse() {

  }
}
