package com.example.gemm_server.service;

import com.example.gemm_server.common.util.WebClientUtil;
import com.example.gemm_server.domain.entity.Activity;
import com.example.gemm_server.domain.entity.Generation;
import com.example.gemm_server.domain.entity.Member;
import com.example.gemm_server.domain.repository.ActivityRepository;
import com.example.gemm_server.domain.repository.GenerationRepository;
import com.example.gemm_server.domain.repository.MemberRepository;
import com.example.gemm_server.dto.generator.request.UpdateGuideRequest;
import com.example.gemm_server.dto.generator.request.GenerateGuideRequest;
import com.example.gemm_server.dto.generator.request.SaveGuideRequest;
import com.example.gemm_server.dto.generator.response.GeneratedGuideResponse;
import com.example.gemm_server.dto.generator.response.UpdatedGuideResponse;
import com.example.gemm_server.dto.generator.response.LlmGuideResponse;
import com.example.gemm_server.dto.generator.response.SavedGenerationResponse;
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

    public GeneratedGuideResponse generateActivityGuide(GenerateGuideRequest generateGuideRequest) {
        LlmGuideResponse llmGuideResponse = webClientUtil.post("/generate/activity/guide",
                generateGuideRequest, LlmGuideResponse.class);

        return new GeneratedGuideResponse(llmGuideResponse.content());
    }

    public SavedGenerationResponse saveActivityGuide(SaveGuideRequest saveGuideRequest, Long memberId) {
        Member member = memberRepository.findOneById(1L);

        Activity activity = Activity.builder().title(saveGuideRequest.title())
                .age(saveGuideRequest.age())
                .content(saveGuideRequest.content())
                .materialType((short) 0)
                .build();
        Activity savedActivity = activityRepository.save(activity);

        Generation generation = Generation.builder()
                .activityId(savedActivity)
                .ownerId(member)
                .build();
        Generation savedGeneration = generationRepository.save(generation);
        return new SavedGenerationResponse(savedGeneration.getId());
    }

    public UpdatedGuideResponse updateActivityGuide(UpdateGuideRequest UpdateGuideRequest) {
        UpdatedGuideResponse updatedGuideResponse = webClientUtil.put("/generate/activity/guide/result",
                UpdateGuideRequest, UpdatedGuideResponse.class);

        return new UpdatedGuideResponse(updatedGuideResponse.content());
    }
}
