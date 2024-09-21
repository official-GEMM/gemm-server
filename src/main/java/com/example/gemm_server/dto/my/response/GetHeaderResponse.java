package com.example.gemm_server.dto.my.response;

import com.example.gemm_server.dto.common.MemberBundle;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
@Schema(description = "헤더 정보 응답", requiredProperties = {"gem", "profileImagePath",
    "hasUnreadNotification"})
public class GetHeaderResponse {

  @Schema(description = "보유 젬")
  private int gem;

  @Schema(description = "프로필 경로")
  private String profileImagePath;

  @Schema(description = "미확인 알림 존재 여부")
  private Boolean hasUnreadNotification;

  public GetHeaderResponse(MemberBundle memberBundle, boolean hasUnreadNotification) {
    this.gem = memberBundle.getMember().getGem();
    this.profileImagePath = memberBundle.getProfileImageUrl();
    this.hasUnreadNotification = hasUnreadNotification;
  }
}