package com.example.gemm_server.dto.my.response;

import com.example.gemm_server.domain.entity.Member;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
public class GetHeaderResponse {

  @Schema(description = "보유 젬")
  private int gem;

  @Schema(description = "프로필 경로")
  private String profileImagePath;

  @Schema(description = "미확인 알림 존재 여부")
  private Boolean hasUnreadNotification;

  public GetHeaderResponse(Member member) {
    this.gem = member.getGem();
    this.profileImagePath = member.getProfileImageUrl();
    this.hasUnreadNotification = false; // TODO: redis에서 사용자 알림 조회
  }
}