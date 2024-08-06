package com.example.gemm_server.dto.generator.response;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "코멘트 단 컷/도안 응답", requiredProperties = {"thumbnailPath", "filePath"})
public record CommentedCutoutResponse(
    @Schema(description = "수정된 컷/도안 썸네일 주소")
    String thumbnailPath,

    @Schema(description = "수정된 컷/도안 파일 주소")
    String filePath
) {

}
