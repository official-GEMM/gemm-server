package com.example.gemm_server.dto.my.request;

import com.example.gemm_server.common.constant.RegularExpression;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateMyNicknameRequest {

  @Schema(description = "사용자 닉네임")
  @NotBlank
  @Size(min = 2, max = 12)
  @Pattern(regexp = RegularExpression.NICKNAME, message = "최소 2, 최대 12의 한글을 사용해주세요. (공백 제외)")
  private String nickname;
}
