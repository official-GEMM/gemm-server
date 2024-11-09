package com.example.gemm_server.dto.common.response;

import com.example.gemm_server.domain.entity.Member;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
@Schema(description = "사용자 정보 응답", requiredProperties = {"memberId", "nickname"})
public class MemberResponse {

  @Schema(description = "사용자 아이디")
  private long memberId;

  @Schema(description = "사용자 닉네임")
  private String nickname;

  public MemberResponse(Member member) {
    this.memberId = member.getId();
    this.nickname = member.getNickname();
  }
}
