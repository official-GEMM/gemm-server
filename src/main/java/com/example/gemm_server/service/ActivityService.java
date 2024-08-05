package com.example.gemm_server.service;

import com.example.gemm_server.common.constant.Policy;
import com.example.gemm_server.common.enums.GemUsageType;
import com.example.gemm_server.common.util.PoiUtil;
import com.example.gemm_server.common.util.S3Util;
import com.example.gemm_server.common.util.WebClientUtil;
import com.example.gemm_server.domain.entity.Activity;
import com.example.gemm_server.domain.entity.Generation;
import com.example.gemm_server.domain.entity.Member;
import com.example.gemm_server.domain.repository.ActivityRepository;
import com.example.gemm_server.domain.repository.GenerationRepository;
import com.example.gemm_server.dto.generator.request.GenerateGuideRequest;
import com.example.gemm_server.dto.generator.request.GenerateMaterialRequest;
import com.example.gemm_server.dto.generator.request.SaveGuideRequest;
import com.example.gemm_server.dto.generator.request.UpdateGuideRequest;
import com.example.gemm_server.dto.generator.response.ActivitySheetPathResponse;
import com.example.gemm_server.dto.generator.response.CutoutPathResponse;
import com.example.gemm_server.dto.generator.response.GeneratedGuideResponse;
import com.example.gemm_server.dto.generator.response.GeneratedMaterialsResponse;
import com.example.gemm_server.dto.generator.response.LlmGuideResponse;
import com.example.gemm_server.dto.generator.response.LlmMaterialResponse;
import com.example.gemm_server.dto.generator.response.PptPathResponse;
import com.example.gemm_server.dto.generator.response.SavedGenerationResponse;
import com.example.gemm_server.dto.generator.response.UpdatedGuideResponse;
import jakarta.transaction.Transactional;
import java.io.File;
import java.util.List;
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
  private final GemService gemService;
  private final MemberService memberService;
  private final S3Util s3Util;

  @Transactional
  public GeneratedGuideResponse generateGuide(GenerateGuideRequest generateGuideRequest,
      Long memberId) {
    Member member = memberService.findMemberByMemberId(memberId);

    LlmGuideResponse llmGuideResponse = webClientUtil.post("/generate/activity/guide",
        generateGuideRequest, LlmGuideResponse.class);

    gemService.saveChangesOfGemWithMember(member, Policy.GENERATE_GUIDE, GemUsageType.AI_USE);

    return new GeneratedGuideResponse(llmGuideResponse.content(), member.getGem());
  }

  @Transactional
  public SavedGenerationResponse saveGuide(SaveGuideRequest saveGuideRequest, Long memberId) {
    Member member = memberService.findMemberByMemberId(memberId);

    Activity activity = Activity.builder().title(saveGuideRequest.title())
        .age(saveGuideRequest.age()).content(saveGuideRequest.content()).materialType((short) 0)
        .build();
    Activity savedActivity = activityRepository.save(activity);

    Generation generation = Generation.builder().activity(savedActivity).owner(member).build();
    Generation savedGeneration = generationRepository.save(generation);
    return new SavedGenerationResponse(savedGeneration.getId());
  }

  @Transactional
  public UpdatedGuideResponse updateGuide(UpdateGuideRequest UpdateGuideRequest, Long memberId) {
    Member member = memberService.findMemberByMemberId(memberId);

    UpdatedGuideResponse updatedGuideResponse = webClientUtil.put("/generate/activity/guide/result",
        UpdateGuideRequest, UpdatedGuideResponse.class);

    gemService.saveChangesOfGemWithMember(member, Policy.UPDATE_GUIDE, GemUsageType.AI_USE);

    return new UpdatedGuideResponse(updatedGuideResponse.content(), member.getGem());
  }

  @Transactional
  public GeneratedMaterialsResponse generateMaterials(
      GenerateMaterialRequest generateMaterialRequest, Long memberId) {
    Member member = memberService.findMemberByMemberId(memberId);

    LlmMaterialResponse llmMaterialResponse = webClientUtil.post("/generate/activity/materials",
        generateMaterialRequest, LlmMaterialResponse.class);

    PptPathResponse pptPathResponse = null;
    if (llmMaterialResponse.ppt() != null) {
      List<String> imagePaths = PoiUtil.pptToImages(
          s3Util.downloadFile(llmMaterialResponse.ppt().fileName()));
      String[] thumbnailPaths = new String[imagePaths.size()];
      for (int i = 0; i < imagePaths.size(); i++) {
        File file = new File(imagePaths.get(i));
        thumbnailPaths[i] = s3Util.getFileUrl(s3Util.uploadFile(file));
      }
      pptPathResponse = new PptPathResponse(thumbnailPaths, llmMaterialResponse.ppt().filePath());
      gemService.saveChangesOfGemWithMember(member, Policy.GENERATE_PPT, GemUsageType.AI_USE);
    }

    ActivitySheetPathResponse activitySheetPathResponse = null;
    if (llmMaterialResponse.activitySheet() != null) {
      gemService.saveChangesOfGemWithMember(member, Policy.GENERATE_ACTIVITY_SHEET,
          GemUsageType.AI_USE);
    }

    CutoutPathResponse cutoutPathResponse = null;
    if (llmMaterialResponse.cutout() != null) {
      cutoutPathResponse = new CutoutPathResponse(llmMaterialResponse.cutout(),
          llmMaterialResponse.cutout());
      gemService.saveChangesOfGemWithMember(member, Policy.GENERATE_CUTOUT, GemUsageType.AI_USE);
    }

    return new GeneratedMaterialsResponse(pptPathResponse, activitySheetPathResponse,
        cutoutPathResponse, member.getGem());
  }
}
