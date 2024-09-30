package com.example.gemm_server.dto.generator.response;

public record LlmPptResponse(
    String fileName,
    String filePath
) {

  public boolean isEmptyFileValue() {
    return fileName().isBlank() || filePath().isBlank();
  }
}
