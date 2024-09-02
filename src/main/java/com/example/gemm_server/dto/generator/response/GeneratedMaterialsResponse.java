package com.example.gemm_server.dto.generator.response;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "생성한 활동 자료 응답", requiredProperties = {"gem"})
public record GeneratedMaterialsResponse(
    @Schema(description = "생성한 PPT 썸네일과 파일 링크")
    PptPathResponse ppt,

    @Schema(description = "생성한 활동지 썸네일과 파일 링크")
    ActivitySheetPathResponse activitySheet,

    @Schema(description = "생성한 컷/도안 썸네일과 파일 링크")
    CutoutPathResponse cutout,

    @Schema(description = "잔여 젬")
    int gem
) {

}
