package com.example.gemm_server.dto.generator.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "생성한 활동 자료 응답", requiredProperties = {"gem"})
public record GeneratedMaterialsResponse(
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Schema(description = "생성한 PPT 썸네일과 파일 링크")
    PptPathResponse ppt,

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Schema(description = "생성한 활동지 썸네일과 파일 링크")
    ActivitySheetPathResponse activitySheet,

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Schema(description = "생성한 컷/도안 썸네일과 파일 링크")
    CutoutPathResponse cutout,

    @Schema(description = "잔여 젬")
    int gem
) {

}
