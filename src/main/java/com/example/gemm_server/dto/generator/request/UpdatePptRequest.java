package com.example.gemm_server.dto.generator.request;

import com.example.gemm_server.common.enums.Category;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.List;

public record UpdatePptRequest(
    @Schema(description = "PPT 파일 주소")
    @NotNull
    String ppt,

    @Schema(description = "생성한 PPT 슬라이드 수")
    @Min(1)
    @Max(20)
    int totalSlides,

    @Schema(description = "교육 대상 연령")
    @NotNull
    Short age,

    @Schema(description = "교육하고자 하는 활동 방식")
    @NotNull
    Category category,

    @Schema(description = "수정에 적용할 코멘트")
    @NotNull
    @Size(min = 1)
    List<CommentPptRequest> comments
) {

}
