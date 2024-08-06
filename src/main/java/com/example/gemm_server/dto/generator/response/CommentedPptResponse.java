package com.example.gemm_server.dto.generator.response;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "코멘트 단 PPT 응답", requiredProperties = {"thumbnailPaths", "filePath"})
public record CommentedPptResponse(
    @Schema(description = "수정된 PPT 썸네일 주소")
    String[] thumbnailPaths,

    @Schema(description = "수정된 PPT 파일 주소")
    String filePath
) {

}
