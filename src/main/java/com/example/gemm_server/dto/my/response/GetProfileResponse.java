package com.example.gemm_server.dto.my.response;

import com.example.gemm_server.dto.common.MemberBundle;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
@Schema(description = "내 프로필 응답", requiredProperties = {"profileImagePath", "referralCode",
    "nickname"})
public class GetProfileResponse {

  @Schema(description = "프로필 이미지")
  private String profileImagePath;

  @Schema(description = "사용자 추천인 코드")
  private String referralCode;

  @Schema(description = "시용자 닉네임")
  private String nickname;


  public GetProfileResponse(MemberBundle memberBundle) {
    this.profileImagePath = memberBundle.getProfileImageUrl();
    this.referralCode = memberBundle.getMember().getReferralCode();
    this.nickname = memberBundle.getMember().getNickname();
  }
}
