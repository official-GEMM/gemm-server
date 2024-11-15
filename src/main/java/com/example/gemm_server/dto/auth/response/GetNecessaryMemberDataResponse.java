package com.example.gemm_server.dto.auth.response;

import com.example.gemm_server.common.enums.Role;
import com.example.gemm_server.domain.entity.Member;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDate;
import lombok.Getter;

@Getter
@Schema(description = "필수 정보 응답", requiredProperties = {"isCompleted",
    "isReferralCompensable, isAdmin"})
public class GetNecessaryMemberDataResponse {

  @JsonInclude(JsonInclude.Include.NON_NULL)
  @Schema(description = "사용자 이름")
  private String name;

  @JsonInclude(JsonInclude.Include.NON_NULL)
  @Schema(description = "사용자 생일")
  private LocalDate birth;

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

  @Schema(description = "관리자 여부")
  private Boolean isAdmin;

  public GetNecessaryMemberDataResponse(Member member) {
    this.name = member.getName();
    this.birth = member.getBirth();
    this.manageAge = member.getManageAge();
    this.nickname = member.getNickname();
    this.phoneNumber = member.getPhoneNumber();
    this.isCompleted = member.isDataCompleted();
    this.isReferralCompensable = !member.getIsRegistrationCompleted();
    this.isAdmin = member.getRole().equals(Role.ADMIN);
  }
}
