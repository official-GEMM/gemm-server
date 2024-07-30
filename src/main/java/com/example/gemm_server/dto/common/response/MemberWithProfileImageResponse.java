package com.example.gemm_server.dto.common.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import org.springframework.core.io.UrlResource;

@Getter
public class MemberWithProfileImageResponse extends MemberResponse {

  @Schema(description = "프로필 이미지 경로")
  private UrlResource profileImagePath;

  public MemberWithProfileImageResponse() {
  }
}
