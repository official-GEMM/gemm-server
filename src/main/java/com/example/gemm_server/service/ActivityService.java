package com.example.gemm_server.service;

import com.example.gemm_server.common.util.WebClientUtil;
import com.example.gemm_server.domain.entity.Activity;
import com.example.gemm_server.domain.entity.Generation;
import com.example.gemm_server.domain.entity.Member;
import com.example.gemm_server.domain.repository.ActivityRepository;
import com.example.gemm_server.domain.repository.GenerationRepository;
import com.example.gemm_server.domain.repository.MemberRepository;
import com.example.gemm_server.dto.generator.request.UpdateGuideRequest;
import com.example.gemm_server.dto.generator.request.GenerateActivityGuideRequest;
import com.example.gemm_server.dto.generator.request.SaveActivityGuideRequest;
import com.example.gemm_server.dto.generator.response.ActivityGuideResponse;
import com.example.gemm_server.dto.generator.response.UpdatedGuideResponse;
import com.example.gemm_server.dto.generator.response.LlmActivityGuideResponse;
import com.example.gemm_server.dto.generator.response.SavedActivityResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class ActivityService {
    private final WebClientUtil webClientUtil;
    private final ActivityRepository activityRepository;
    private final GenerationRepository generationRepository;
    private final MemberRepository memberRepository;

    public ActivityGuideResponse generateActivityGuide(GenerateActivityGuideRequest generateActivityGuideRequest) {
        LlmActivityGuideResponse llmActivityGuideResponse = webClientUtil.post("/generate/activity/guide",
                generateActivityGuideRequest, LlmActivityGuideResponse.class);
        log.info(llmActivityGuideResponse.content());

        return new ActivityGuideResponse(llmActivityGuideResponse.content());
    }

    public SavedActivityResponse saveActivityGuide(SaveActivityGuideRequest saveActivityGuideRequest, Long memberId) {
        Member member = memberRepository.findOneById(1L);

        Activity activity = Activity.builder().title(saveActivityGuideRequest.title())
                .age(saveActivityGuideRequest.age())
                .content(saveActivityGuideRequest.content())
                .materialType((short) 0)
                .build();
        Activity savedActivity = activityRepository.save(activity);

        Generation generation = Generation.builder()
                .activityId(savedActivity)
                .ownerId(member)
                .build();
        Generation savedGeneration = generationRepository.save(generation);
        return new SavedActivityResponse(savedGeneration.getId());
    }

    public UpdatedGuideResponse updateActivityGuide(UpdateGuideRequest UpdateGuideRequest) {
        UpdatedGuideResponse updatedGuideResponse = webClientUtil.put("/generate/activity/guide/result",
                UpdateGuideRequest, UpdatedGuideResponse.class);
        log.info(updatedGuideResponse.content());

        return new UpdatedGuideResponse(updatedGuideResponse.content());
    }
}
