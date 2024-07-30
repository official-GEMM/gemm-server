package com.example.gemm_server.dto.my.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import org.springframework.core.io.UrlResource;

@Getter
public class GetHeaderResponse {

  @Schema(description = "보유 젬")
  private int gem;

  @Schema(description = "프로필 경로")
  private UrlResource profileImagePath;

  @Schema(description = "미확인 알림 존재 여부")
  private Boolean hasUnreadNotification;

  public GetHeaderResponse() {
  }
}