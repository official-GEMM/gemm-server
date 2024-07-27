package com.example.gemm_server.dto.auth.request;

import com.example.gemm_server.common.constant.RegularExpression;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PostNecessaryMemberDataRequest {

  @Schema(description = "사용자 이름")
  @NotBlank
  @Size(min = 2, max = 30)
  @Pattern(regexp = RegularExpression.NAME, message = "최소 2, 최대 12의 한글 또는 영문을 사용해주세요. (공백 제외)")
  private String name;

  @Schema(description = "사용자 생일")
  @NotBlank
  @Pattern(regexp = RegularExpression.DATE, message = "YYYY-MM-dd의 형식을 사용해주세요.")
  private String birth;

  @Schema(description = "관리 대상자의 연령")
  @NotNull
  private Short manageAge;

  @Schema(description = "사용자 닉네임")
  @NotBlank
  @Size(min = 2, max = 12)
  @Pattern(regexp = RegularExpression.NICKNAME, message = "최소 2, 최대 12의 한글을 사용해주세요. (공백 제외)")
  private String nickname;

  @Schema(description = "전화번호")
  @NotBlank
  @Pattern(regexp = RegularExpression.PHONE_NUMBER, message = "010-1234-1234의 형식을 사용해주세요.")
  private String phoneNumber;

  @Schema(description = "이 사이트를 추천한 사용자의 추천인 코드")
  private String referralCode;
}
