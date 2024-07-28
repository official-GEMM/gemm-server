package com.example.gemm_server.service;

import com.example.gemm_server.common.util.WebClientUtil;
import com.example.gemm_server.dto.generator.request.GenerateActivityGuideRequest;
import com.example.gemm_server.dto.generator.response.ActivityGuideResponse;
import com.example.gemm_server.dto.generator.response.LlmActivityGuideResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class ActivityService {
    private final WebClientUtil webClientUtil;

    public ActivityGuideResponse generateActivityGuide(GenerateActivityGuideRequest generateActivityGuideRequest) {
        LlmActivityGuideResponse llmActivityGuideResponse = webClientUtil.post("http://localhost:8000/generate/activity/guide",
                generateActivityGuideRequest, LlmActivityGuideResponse.class);
        log.info(llmActivityGuideResponse.content());

        return new ActivityGuideResponse(llmActivityGuideResponse.content());
    }
}
