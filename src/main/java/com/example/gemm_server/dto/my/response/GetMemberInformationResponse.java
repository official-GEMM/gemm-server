package com.example.gemm_server.dto.my.response;

import com.example.gemm_server.domain.entity.Member;
import com.example.gemm_server.dto.common.MemberBundle;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
@Schema(description = "내 정보 응답", requiredProperties = {"manageAge", "phoneNumber",
    "profileImagePath", "nickname", "referralCode"})
public class GetMemberInformationResponse {

  @Schema(description = "관리 대상 연령")
  private short manageAge;

  @Schema(description = "전화번호")
  private String phoneNumber;

  @Schema(description = "프로필 이미지")
  private String profileImagePath;

  @Schema(description = "시용자 닉네임")
  private String nickname;

  @Schema(description = "사용자 추천인 코드")
  private String referralCode;

  public GetMemberInformationResponse(MemberBundle memberBundle) {
    Member member = memberBundle.getMember();
    this.manageAge = member.getManageAge();
    this.phoneNumber = member.getPhoneNumber();
    this.profileImagePath = memberBundle.getProfileImageUrl();
    this.nickname = member.getNickname();
    this.referralCode = member.getReferralCode();
  }
}
