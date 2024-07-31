package com.example.gemm_server.dto.generator.response;

import com.example.gemm_server.common.enums.Category;
import io.swagger.v3.oas.annotations.media.Schema;

public record LinkedMaterialGuideResponse(
        @Schema(description = "생성한 주제")
        String title,
        @Schema(description = "교육 대상 연령")
        Short age,
        @Schema(description = "교육하고자 하는 활동 방식")
        Category category,
        @Schema(description = "생성한 내용")
        String content,
        @Schema(description = "생성한 PPT 설계 내용")
        String[] ppt,
        @Schema(description = "생성한 활동지 설계 내용")
        String activitySheet,
        @Schema(description = "생성한 컷/도안 설계 내용")
        String cutout
) {
}
