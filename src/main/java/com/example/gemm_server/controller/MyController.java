package com.example.gemm_server.controller;

import static com.example.gemm_server.common.constant.Policy.DEAL_PAGE_SIZE;
import static com.example.gemm_server.common.constant.Policy.SCRAP_PAGE_SIZE;

import com.example.gemm_server.common.annotation.auth.BearerAuth;
import com.example.gemm_server.domain.entity.Deal;
import com.example.gemm_server.domain.entity.Member;
import com.example.gemm_server.domain.entity.Notification;
import com.example.gemm_server.domain.entity.ProfileImage;
import com.example.gemm_server.domain.entity.Scrap;
import com.example.gemm_server.dto.CommonResponse;
import com.example.gemm_server.dto.EmptyDataResponse;
import com.example.gemm_server.dto.common.MemberBundle;
import com.example.gemm_server.dto.common.PageInfo;
import com.example.gemm_server.dto.common.response.GemResponse;
import com.example.gemm_server.dto.my.NotificationBundle;
import com.example.gemm_server.dto.my.ScrapBundle;
import com.example.gemm_server.dto.my.request.UpdateMyInformationRequest;
import com.example.gemm_server.dto.my.request.UpdateProfileImageRequest;
import com.example.gemm_server.dto.my.response.GetHeaderResponse;
import com.example.gemm_server.dto.my.response.GetMemberInformationResponse;
import com.example.gemm_server.dto.my.response.GetMyPurchasesResponse;
import com.example.gemm_server.dto.my.response.GetMySalesResponse;
import com.example.gemm_server.dto.my.response.GetMyScrapsResponse;
import com.example.gemm_server.dto.my.response.GetNotificationsByUserResponse;
import com.example.gemm_server.dto.my.response.GetProfileResponse;
import com.example.gemm_server.dto.my.response.UpdateMyInformationResponse;
import com.example.gemm_server.dto.my.response.UpdateProfileImageResponse;
import com.example.gemm_server.dto.storage.DealBundle;
import com.example.gemm_server.security.jwt.CustomUser;
import com.example.gemm_server.service.AuthService;
import com.example.gemm_server.service.DealService;
import com.example.gemm_server.service.MemberService;
import com.example.gemm_server.service.NotificationService;
import com.example.gemm_server.service.ProfileImageService;
import com.example.gemm_server.service.ScrapService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@BearerAuth
@Validated
@RequiredArgsConstructor
@RestController()
@RequestMapping("/my")
@Tag(name = "My", description = "사용자 정보 관리 API")
public class MyController {

  private final MemberService memberService;
  private final AuthService authService;
  private final NotificationService notificationService;
  private final ProfileImageService profileImageService;
  private final DealService dealService;
  private final ScrapService scrapService;

  @Operation(summary = "내 정보 조회", description = "로그인한 사용자의 정보를 가져오는 API")
  @GetMapping()
  public ResponseEntity<CommonResponse<GetMemberInformationResponse>> getMyInformation(
      @AuthenticationPrincipal CustomUser user
  ) {
    Member member = memberService.findMemberByMemberIdOrThrow(user.getId());
    MemberBundle memberBundle = memberService.convertToMemberBundle(member);
    GetMemberInformationResponse response = new GetMemberInformationResponse(memberBundle);
    return ResponseEntity.ok(new CommonResponse<>(response));
  }

  @Operation(summary = "내 프로필 조회", description = "로그인한 사용자의 프로필을 가져오는 API")
  @GetMapping("/profile")
  public ResponseEntity<CommonResponse<GetProfileResponse>> getMyProfile(
      @AuthenticationPrincipal CustomUser user
  ) {
    Member member = memberService.findMemberByMemberIdOrThrow(user.getId());
    MemberBundle memberBundle = memberService.convertToMemberBundle(member);
    GetProfileResponse response = new GetProfileResponse(memberBundle);
    return ResponseEntity.ok(new CommonResponse<>(response));
  }

  @Operation(summary = "내 정보 변경", description = "로그인한 사용자의 관리 연령과 휴대전화 번호를 변경하는 API")
  @PutMapping()
  public ResponseEntity<CommonResponse<UpdateMyInformationResponse>> updateMyInformation(
      @Valid @RequestBody UpdateMyInformationRequest updateMyInformationRequest,
      @AuthenticationPrincipal CustomUser user
  ) {
    memberService.validateNicknameForUpdate(user.getId(), updateMyInformationRequest.getNickname());
    authService.validatePhoneNumberForUpdate(user.getId(),
        updateMyInformationRequest.getPhoneNumber());
    Member member = memberService.updateMyInformation(user.getId(), updateMyInformationRequest);
    UpdateMyInformationResponse response = new UpdateMyInformationResponse(member);
    return ResponseEntity.ok(new CommonResponse<>(response));
  }

  @Operation(summary = "회원 탈퇴", description = "회원 탈퇴 API")
  @DeleteMapping()
  public ResponseEntity<EmptyDataResponse> withdraw(
      @AuthenticationPrincipal CustomUser user
  ) {
    memberService.withdrawMember(user.getId());
    return ResponseEntity.ok(new EmptyDataResponse());
  }

  @Operation(summary = "내 보유 젬 조회", description = "사용자의 보유한 젬의 수를 조회하는 API")
  @GetMapping("/gem")
  public ResponseEntity<CommonResponse<GemResponse>> getGemCount(
      @AuthenticationPrincipal CustomUser user
  ) {
    Member member = memberService.findMemberByMemberIdOrThrow(user.getId());
    GemResponse response = new GemResponse(member.getGem());
    return ResponseEntity.ok(new CommonResponse<>(response));
  }

  @Operation(summary = "헤더 정보 조회", description = "헤더에 필요한 사용자의 정보를 가져오는 API")
  @GetMapping("/header")
  public ResponseEntity<CommonResponse<GetHeaderResponse>> getHeaderInformation(
      @AuthenticationPrincipal CustomUser user
  ) {
    Member member = memberService.findMemberByMemberIdOrThrow(user.getId());
    MemberBundle memberBundle = memberService.convertToMemberBundle(member);
    boolean hasUnreadNotification =
        notificationService.countOfUnopenedNotifications(user.getId()) > 0;
    GetHeaderResponse response = new GetHeaderResponse(memberBundle, hasUnreadNotification);
    return ResponseEntity.ok(new CommonResponse<>(response));
  }

  @Operation(summary = "알림 조회", description = "사용자의 알림을 가져오는 API")
  @GetMapping("/notifications")
  public ResponseEntity<CommonResponse<GetNotificationsByUserResponse>> getMyNotifications(
      @AuthenticationPrincipal CustomUser user
  ) {
    List<Notification> notifications = notificationService.getRecentNotificationsByMember(
        user.getId());
    notificationService.markNotificationsAsOpened(notifications);
    List<NotificationBundle> notificationBundles = notificationService.convertToNotificationBundle(
        notifications);
    GetNotificationsByUserResponse notificationsResponse = new GetNotificationsByUserResponse(
        notificationBundles);
    return ResponseEntity.ok(new CommonResponse<>(notificationsResponse));
  }

  @Operation(summary = "프로필 이미지 변경", description = "사용자의 프로필 이미지를 변경하는 API")
  @PutMapping(value = "/profile-image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
  public ResponseEntity<CommonResponse<UpdateProfileImageResponse>> getMyNotifications(
      @Valid @ModelAttribute UpdateProfileImageRequest request,
      @AuthenticationPrincipal CustomUser user
  ) {
    profileImageService.deleteProfileImageIfExist(user.getId());
    ProfileImage profileImage = profileImageService.save(user.getId(), request.getProfileImage());
    UpdateProfileImageResponse response = new UpdateProfileImageResponse(profileImage);
    return ResponseEntity.ok(new CommonResponse<>(response));
  }

  @BearerAuth
  @Operation(summary = "내 스크랩 조회", description = "사용자의 스크랩 리스트를 가져오는 API")
  @GetMapping("/scraps")
  public ResponseEntity<CommonResponse<GetMyScrapsResponse>> getMyScraps(
      @RequestParam("page") @Min(1) Integer page,
      @AuthenticationPrincipal CustomUser user
  ) {
    Page<Scrap> scraps = scrapService.getScrapsByMemberIdOrderByCreatedAt(user.getId(), page - 1,
        SCRAP_PAGE_SIZE);
    List<ScrapBundle> scrapBundles = scrapService.convertToScrapBundle(scraps.getContent(),
        user.getId());
    PageInfo pageInfo = new PageInfo(page, scraps.getTotalPages());
    GetMyScrapsResponse response = new GetMyScrapsResponse(scrapBundles, pageInfo);
    return ResponseEntity.ok(new CommonResponse<>(response));
  }

  @BearerAuth
  @Operation(summary = "내 구매 내역 조회", description = "사용자의 마켓 상품 구매 내역을 가져오는 API")
  @GetMapping("/history/purchases")
  public ResponseEntity<CommonResponse<GetMyPurchasesResponse>> getMyPurchases(
      @RequestParam("page") @Min(1) Integer page,
      @RequestParam("year") Integer year,
      @RequestParam("month") @Min(1) @Max(12) Short month,
      @AuthenticationPrincipal CustomUser user
  ) {
    Sort sort = Sort.by(Direction.DESC, "createdAt");
    Page<Deal> deals = dealService.getDealsByBuyerId(user.getId(), page - 1, DEAL_PAGE_SIZE, sort,
        year, month);
    List<DealBundle> dealBundles = dealService.convertToDealBundle(deals.getContent());
    PageInfo pageInfo = new PageInfo(page, deals.getTotalPages());
    GetMyPurchasesResponse response = new GetMyPurchasesResponse(dealBundles, pageInfo);
    return ResponseEntity.ok(new CommonResponse<>(response));
  }

  @BearerAuth
  @Operation(summary = "내 판매 내역 조회", description = "사용자의 마켓 상품 판매 내역을 가져오는 API")
  @GetMapping("/history/sales")
  public ResponseEntity<CommonResponse<GetMySalesResponse>> getMySales(
      @RequestParam("page") @Min(1) Integer page,
      @RequestParam("year") Integer year,
      @RequestParam("month") @Min(1) @Max(12) Short month,
      @AuthenticationPrincipal CustomUser user
  ) {
    Sort sort = Sort.by(Direction.DESC, "createdAt");
    Page<Deal> deals = dealService.getDealsBySellerId(user.getId(), page - 1, DEAL_PAGE_SIZE, sort,
        year, month);
    List<DealBundle> dealBundles = dealService.convertToDealBundle(deals.getContent());
    PageInfo pageInfo = new PageInfo(page, deals.getTotalPages());
    GetMySalesResponse response = new GetMySalesResponse(dealBundles, pageInfo);
    return ResponseEntity.ok(new CommonResponse<>(response));
  }
}
