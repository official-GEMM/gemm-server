package com.example.gemm_server.dto.generator.response;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "PPT 응답", requiredProperties = {"thumbnailPaths", "filePath"})
public record PptPathResponse(
    @Schema(description = "슬라이드별 썸네일 주소")
    String[] thumbnailPaths,

    @Schema(description = "파일 주소")
    String filePath
) {

}
