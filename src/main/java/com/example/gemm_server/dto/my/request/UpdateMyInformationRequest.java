package com.example.gemm_server.dto.my.request;

import com.example.gemm_server.common.constant.RegularExpression;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;

@Getter
public class UpdateMyInformationRequest {

  @Schema(description = "관리 대상자의 연령")
  @NotNull
  private Short manageAge;

  @Schema(description = "전화번호")
  @NotBlank
  @Pattern(regexp = RegularExpression.PHONE_NUMBER, message = "010-1234-1234의 형식을 사용해주세요.")
  private String phoneNumber;
}
