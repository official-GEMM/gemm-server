package com.example.gemm_server.dto.my.response;

import com.example.gemm_server.domain.entity.Member;
import com.example.gemm_server.dto.common.MemberBundle;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDate;
import lombok.Getter;

@Getter
@Schema(description = "내 정보 응답", requiredProperties = {"phoneNumber", "profileImagePath",
    "nickname", "birth"})
public class GetMemberInformationResponse {

  @Schema(description = "관리 대상 연령")
  @JsonInclude(JsonInclude.Include.NON_NULL)
  private Short manageAge;

  @Schema(description = "전화번호")
  private String phoneNumber;

  @Schema(description = "프로필 이미지")
  private String profileImagePath;

  @Schema(description = "시용자 닉네임")
  private String nickname;

  @Schema(description = "시용자 생일")
  private LocalDate birth;

  public GetMemberInformationResponse(MemberBundle memberBundle) {
    Member member = memberBundle.getMember();
    this.manageAge = member.getManageAge();
    this.phoneNumber = member.getPhoneNumber();
    this.profileImagePath = memberBundle.getProfileImageUrl();
    this.nickname = member.getNickname();
    this.birth = member.getBirth();
  }
}
