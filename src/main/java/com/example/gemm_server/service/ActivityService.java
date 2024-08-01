package com.example.gemm_server.service;

import com.example.gemm_server.common.constant.Policy;
import com.example.gemm_server.common.enums.GemUsageType;
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
import com.example.gemm_server.dto.generator.response.*;
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
    private final GemService gemService;

    public GeneratedGuideResponse generateGuide(GenerateGuideRequest generateGuideRequest, Long memberId) {
        Member member = memberRepository.findOneById(memberId);

        LlmGuideResponse llmGuideResponse = webClientUtil.post("/generate/activity/guide",
                generateGuideRequest, LlmGuideResponse.class);

        gemService.saveChangesOfGemWithMember(member, Policy.GENERATE_GUIDE, GemUsageType.AI_USE);

        return new GeneratedGuideResponse(llmGuideResponse.content(), member.getGem());
    }

    public SavedGenerationResponse saveGuide(SaveGuideRequest saveGuideRequest, Long memberId) {
        Member member = memberRepository.findOneById(memberId);

        Activity activity = Activity.builder().title(saveGuideRequest.title())
                .age(saveGuideRequest.age())
                .content(saveGuideRequest.content())
                .materialType((short) 0)
                .build();
        Activity savedActivity = activityRepository.save(activity);

        Generation generation = Generation.builder()
        .activity(savedActivity)
        .owner(member)
                .build();
        Generation savedGeneration = generationRepository.save(generation);
        return new SavedGenerationResponse(savedGeneration.getId());
    }

    public UpdatedGuideResponse updateGuide(UpdateGuideRequest UpdateGuideRequest, Long memberId) {
        Member member = memberRepository.findOneById(memberId);

        UpdatedGuideResponse updatedGuideResponse = webClientUtil.put("/generate/activity/guide/result",
                UpdateGuideRequest, UpdatedGuideResponse.class);

        gemService.saveChangesOfGemWithMember(member, Policy.UPDATE_GUIDE, GemUsageType.AI_USE);

        return new UpdatedGuideResponse(updatedGuideResponse.content(), member.getGem());
    }


}
