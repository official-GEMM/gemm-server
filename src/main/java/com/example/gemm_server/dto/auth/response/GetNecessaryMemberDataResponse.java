package com.example.gemm_server.dto.auth.response;

import com.example.gemm_server.domain.entity.Member;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
public class GetNecessaryMemberDataResponse {

  @JsonInclude(JsonInclude.Include.NON_NULL)
  @Schema(description = "사용자 이름")
  private String name;

  @JsonInclude(JsonInclude.Include.NON_NULL)
  @Schema(description = "사용자 생일")
  private String birth;

  @JsonInclude(JsonInclude.Include.NON_NULL)
  @Schema(description = "관리 대상자의 연령")
  private Short manageAge;

  @JsonInclude(JsonInclude.Include.NON_NULL)
  @Schema(description = "사용자 닉네임")
  private String nickname;

  @JsonInclude(JsonInclude.Include.NON_NULL)
  @Schema(description = "전화번호")
  private String phoneNumber;

  @Schema(description = "모든 필수 정보 작성 여부")
  private Boolean isCompleted;

  @Schema(description = "추천인 보상 가능 여부")
  private Boolean isReferralCompensable;

  public GetNecessaryMemberDataResponse(Member member) {
    this.name = member.getName();
    this.birth = member.getBirth().toString();
    this.manageAge = member.getManageAge();
    this.nickname = member.getNickname();
    this.phoneNumber = member.getPhoneNumber();
    this.isCompleted = member.isDataCompleted();
    this.isReferralCompensable = !member.getIsRegistrationCompleted();
  }
}
