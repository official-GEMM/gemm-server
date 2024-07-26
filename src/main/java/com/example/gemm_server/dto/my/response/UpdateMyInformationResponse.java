package com.example.gemm_server.dto.my.response;

import com.example.gemm_server.domain.entity.Member;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
public class UpdateMyInformationResponse {

  @Schema(description = "관리 대상자의 연령")
  private short manageAge;

  @Schema(description = "전화번호")
  private String phoneNumber;

  public UpdateMyInformationResponse(Member member) {
    this.manageAge = member.getManageAge();
    this.phoneNumber = member.getPhoneNumber();
  }
}
