package com.example.gemm_server.common.util;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
@RequiredArgsConstructor
public class WebClientUtil {
    private final WebClient webClient;

    public <T, V> V post(String url, T requestDto, Class<V> responseDto) {
        return webClient.post()
                .uri(url)
                .bodyValue(requestDto)
                .retrieve()
                .bodyToMono(responseDto)
                .block();

    }

    public <T, V> V put(String url, T requestDto, Class<V> responseDto) {
       return webClient.put()
                .uri(url)
                .bodyValue(requestDto)
                .retrieve()
                .bodyToMono(responseDto)
                .block();
    }
}
