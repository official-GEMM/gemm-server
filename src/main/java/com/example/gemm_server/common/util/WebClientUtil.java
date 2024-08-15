package com.example.gemm_server.common.util;

import com.example.gemm_server.common.code.error.GeneratorErrorCode;
import com.example.gemm_server.exception.GeneratorException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
@RequiredArgsConstructor
public class WebClientUtil {

  private final WebClient webClient;

  public <T, V> V post(String url, T requestDto, Class<V> responseDto) {
    try {
      return webClient.post()
          .uri(url)
          .bodyValue(requestDto)
          .retrieve()
          .bodyToMono(responseDto)
          .block();
    } catch (Exception e) {
      throw new GeneratorException(GeneratorErrorCode.FAILED_TO_CONNECT_TO_LLM_SERVER);
    }
  }

  public <T, V> V put(String url, T requestDto, Class<V> responseDto) {
    try {
      return webClient.put()
          .uri(url)
          .bodyValue(requestDto)
          .retrieve()
          .bodyToMono(responseDto)
          .block();
    } catch (Exception e) {
      throw new GeneratorException(GeneratorErrorCode.FAILED_TO_CONNECT_TO_LLM_SERVER);
    }
  }
}
