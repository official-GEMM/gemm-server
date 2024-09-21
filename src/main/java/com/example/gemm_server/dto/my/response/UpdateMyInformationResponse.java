package com.example.gemm_server.dto.my.response;

import com.example.gemm_server.domain.entity.Member;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDate;
import lombok.Getter;

@Getter
@Schema(description = "내 정보 수정 응답", requiredProperties = {"manageAge", "phoneNumber", "nickname",
    "birth"})
public class UpdateMyInformationResponse {

  @Schema(description = "관리 대상자의 연령")
  private short manageAge;

  @Schema(description = "전화번호")
  private String phoneNumber;

  @Schema(description = "관리 대상자의 연령")
  private String nickname;

  @Schema(description = "전화번호")
  private LocalDate birth;

  public UpdateMyInformationResponse(Member member) {
    this.manageAge = member.getManageAge();
    this.phoneNumber = member.getPhoneNumber();
    this.nickname = member.getNickname();
    this.birth = member.getBirth();
  }
}
