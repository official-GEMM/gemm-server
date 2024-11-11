package com.example.gemm_server.dto.generator.response;

public record LlmPptResponse(
    String fileName,
    String filePath,
    Float layoutCompleteness,
    Float readability,
    Short generationTime
) {

  public boolean isEmptyFileValue() {
    return fileName().isBlank() || filePath().isBlank();
  }
}
