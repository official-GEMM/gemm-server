package com.example.gemm_server.dto.common.request;

import com.example.gemm_server.common.enums.Category;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.List;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
public class ActivityDetailRequest {

  @NotBlank
  @Size(max = 20)
  @Schema(description = "제목")
  private String title;

  @NotNull
  @Schema(description = "타겟 연령")
  private short age;

  @NotNull
  @Schema(description = "가격")
  private int price;

  @NotNull
  @Schema(description = "영역 및 활동")
  private Category category;

  @NotBlank
  @Size(max = 2000)
  @Schema(description = "내용")
  private String content;

  @Min(2020)
  @Schema(description = "연도")
  private short year;

  @Min(1)
  @Max(12)
  @Schema(description = "월")
  private short month;

  @NotNull
  @Size(max = 5)
  @Schema(description = "업로드할 자료 리스트")
  private List<MultipartFile> materials;
}
