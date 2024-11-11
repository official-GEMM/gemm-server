package com.example.gemm_server.controller;

import static com.example.gemm_server.common.constant.Policy.MAIN_MOST_SCRAPPED_PAGE_SIZE;
import static com.example.gemm_server.common.constant.Policy.MAIN_RECOMMENDED_PAGE_SIZE;
import static com.example.gemm_server.common.constant.Policy.MARKET_SEARCH_PAGE_SIZE;
import static com.example.gemm_server.common.constant.Policy.MARKET_SELLER_OTHERS_PAGE_SIZE;
import static com.example.gemm_server.common.constant.Policy.REVIEW_PAGE_SIZE;

import com.example.gemm_server.common.annotation.auth.AuthorizeOwner;
import com.example.gemm_server.common.annotation.auth.BearerAuth;
import com.example.gemm_server.common.enums.GemUsageType;
import com.example.gemm_server.common.enums.MaterialType;
import com.example.gemm_server.common.enums.Order;
import com.example.gemm_server.common.enums.ReviewOrder;
import com.example.gemm_server.common.util.MaterialUtil;
import com.example.gemm_server.domain.entity.Activity;
import com.example.gemm_server.domain.entity.Banner;
import com.example.gemm_server.domain.entity.Deal;
import com.example.gemm_server.domain.entity.MarketItem;
import com.example.gemm_server.domain.entity.Material;
import com.example.gemm_server.domain.entity.Member;
import com.example.gemm_server.domain.entity.Review;
import com.example.gemm_server.dto.CommonResponse;
import com.example.gemm_server.dto.EmptyDataResponse;
import com.example.gemm_server.dto.common.MemberBundle;
import com.example.gemm_server.dto.common.PageInfo;
import com.example.gemm_server.dto.common.TypedMaterialFile;
import com.example.gemm_server.dto.common.response.DownloadMaterialResponse;
import com.example.gemm_server.dto.common.response.GemResponse;
import com.example.gemm_server.dto.market.MarketItemBundle;
import com.example.gemm_server.dto.market.ReviewBundle;
import com.example.gemm_server.dto.market.ReviewResponse;
import com.example.gemm_server.dto.market.request.PostMarketItemRequest;
import com.example.gemm_server.dto.market.request.PostReviewRequest;
import com.example.gemm_server.dto.market.request.SearchQueryRequest;
import com.example.gemm_server.dto.market.request.UpdateMarketItemRequest;
import com.example.gemm_server.dto.market.response.GetMainResponse;
import com.example.gemm_server.dto.market.response.GetMarketItemsOfSellerResponse;
import com.example.gemm_server.dto.market.response.GetMarketItemsResponse;
import com.example.gemm_server.dto.market.response.GetOtherMarketItemsOfSellerResponse;
import com.example.gemm_server.dto.market.response.GetReviewsForMarketItemResponse;
import com.example.gemm_server.dto.market.response.MarketItemDetailResponse;
import com.example.gemm_server.dto.market.response.MarketItemIdResponse;
import com.example.gemm_server.security.jwt.CustomUser;
import com.example.gemm_server.service.ActivityService;
import com.example.gemm_server.service.BannerService;
import com.example.gemm_server.service.DealService;
import com.example.gemm_server.service.GemService;
import com.example.gemm_server.service.MarketItemService;
import com.example.gemm_server.service.MaterialService;
import com.example.gemm_server.service.MemberService;
import com.example.gemm_server.service.ReviewService;
import com.example.gemm_server.service.ScrapService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@Validated
@RequiredArgsConstructor
@RestController()
@RequestMapping("/market")
@Tag(name = "Maret", description = "마켓 API")
public class MarketController {

  private final MarketItemService marketItemService;
  private final BannerService bannerService;
  private final MaterialService materialService;
  private final MemberService memberService;
  private final ScrapService scrapService;
  private final DealService dealService;
  private final GemService gemService;
  private final ReviewService reviewService;
  private final ActivityService activityService;

  @Operation(summary = "메인", description = "메인 페이지에 필요한 정보를 가져오는 API")
  @GetMapping("/main")
  public ResponseEntity<CommonResponse<GetMainResponse>> getMain(
      @AuthenticationPrincipal CustomUser user
  ) {
    Long memberId = CustomUser.getId(user);
    Sort recommededSort = Order.RECOMMENDED.getSort();
    List<MarketItem> recommendedMarketItems = marketItemService.getMarketItemsOrderBy(
        0, MAIN_RECOMMENDED_PAGE_SIZE, recommededSort).getContent();

    Sort scrapSort = Order.SCRAP.getSort();
    List<MarketItem> mostScrappedMarketItems = marketItemService.getMarketItemsOrderBy(
        0, MAIN_MOST_SCRAPPED_PAGE_SIZE, scrapSort).getContent();

    List<MarketItemBundle> recommendedMarketItemBundles =
        marketItemService.convertToMarketItemBundle(recommendedMarketItems, memberId);
    List<MarketItemBundle> mostScrappedMarketItemBundles =
        marketItemService.convertToMarketItemBundle(mostScrappedMarketItems, memberId);

    List<Banner> banners = bannerService.getBanners();
    GetMainResponse response = new GetMainResponse(banners, recommendedMarketItemBundles,
        mostScrappedMarketItemBundles);
    return ResponseEntity.ok(new CommonResponse<>(response));
  }

  @Operation(summary = "검색", description = "마켓 내의 상품을 검색하는 API")
  @GetMapping("/search")
  public ResponseEntity<CommonResponse<GetMarketItemsResponse>> search(
      @Valid @ModelAttribute SearchQueryRequest request,
      @AuthenticationPrincipal CustomUser user
  ) {
    Integer page = request.getPage();
    Page<MarketItem> marketItems = marketItemService.searchMarketItems(request.getSearch(),
        request.getFilter(), request.getOrder(), page - 1, MARKET_SEARCH_PAGE_SIZE);

    Long memberId = CustomUser.getId(user);
    List<MarketItemBundle> marketItemBundles =
        marketItemService.convertToMarketItemBundle(marketItems.getContent(), memberId);
    PageInfo pageInfo = new PageInfo(page, marketItems.getTotalPages());

    GetMarketItemsResponse response = new GetMarketItemsResponse(marketItemBundles, pageInfo);
    return ResponseEntity.ok(new CommonResponse<>(response));
  }

  @Operation(summary = "상품 상세", description = "상품의 상세 정보를 조회하는 API")
  @GetMapping("/{marketItemId}")
  public ResponseEntity<CommonResponse<MarketItemDetailResponse>> getMarketItemDetail(
      @PathVariable("marketItemId") Long marketItemId,
      @AuthenticationPrincipal CustomUser user
  ) {
    Long memberId = CustomUser.getId(user);
    MarketItem marketItem = marketItemService.findMarketItemOrThrow(marketItemId);
    List<Material> materials = materialService.getMaterialsWithThumbnailByActivityId(
        marketItem.getActivity().getId());
    boolean isScrapped = scrapService.isScrapped(memberId, marketItemId);
    boolean isPurchased = dealService.isPurchased(memberId, marketItem.getActivity().getId());

    MarketItemDetailResponse response = new MarketItemDetailResponse(marketItem, materials,
        isScrapped, isPurchased, memberId);
    return ResponseEntity.ok(new CommonResponse<>(response));
  }

  @Operation(summary = "판매자의 다른 상품", description = "판매자의 다른 상품을 추천순으로 조회하는 API")
  @GetMapping("/{marketItemId}/others")
  public ResponseEntity<CommonResponse<GetOtherMarketItemsOfSellerResponse>> getOtherMarketItemsOfSeller(
      @PathVariable("marketItemId") Long marketItemId
  ) {
    Member owner = marketItemService.findOwner(marketItemId);
    Sort newestFirstSort = Order.NEWEST_FIRST.getSort();
    Page<MarketItem> marketItems = marketItemService.getMarketItemsByOwnerOrderBy(owner.getId(), 0,
        MARKET_SELLER_OTHERS_PAGE_SIZE, newestFirstSort);
    List<MarketItemBundle> marketItemBundles =
        marketItemService.convertToMarketItemBundle(marketItems.getContent(), owner.getId());
    GetOtherMarketItemsOfSellerResponse response = new GetOtherMarketItemsOfSellerResponse(
        marketItemBundles);
    return ResponseEntity.ok(new CommonResponse<>(response));
  }

  @BearerAuth
  @AuthorizeOwner(Deal.class)
  @Operation(summary = "자료 다운로드", description = "상품의 활동 자료들을 다운로드하는 API")
  @GetMapping("/{marketItemId}/download")
  public ResponseEntity<CommonResponse<DownloadMaterialResponse>> downloadMaterials(
      @PathVariable("marketItemId") Long marketItemId
  ) {
    MarketItem marketItem = marketItemService.getMarketItemWithActivityOrThrow(marketItemId);
    List<Material> materials = materialService.getMaterialsByActivityId(
        marketItem.getActivity().getId());
    DownloadMaterialResponse response = new DownloadMaterialResponse(materials);
    return ResponseEntity.ok(new CommonResponse<>(response));
  }

  @BearerAuth
  @Operation(summary = "상품 구매", description = "마켓의 상품을 구매하는 API")
  @PostMapping("/{marketItemId}/buy")
  public ResponseEntity<CommonResponse<GemResponse>> buyMarketItem(
      @PathVariable("marketItemId") Long marketItemId,
      @AuthenticationPrincipal CustomUser user
  ) {
    MarketItem marketItem = marketItemService.findMarketItemOrThrow(marketItemId);
    Member seller = marketItem.getOwner();
    Member buyer = memberService.findMemberByMemberIdOrThrow(user.getId());
    marketItemService.validatePurchasable(seller.getId(), buyer.getId(),
        marketItem.getActivity().getId());

    Integer price = marketItem.getPrice();
    dealService.savePurchaseForMarketItem(marketItem, buyer.getId());
    gemService.saveChangesOfGemWithMember(buyer, price, GemUsageType.MARKET_PURCHASE);
    gemService.saveChangesOfGemWithMember(seller, price, GemUsageType.MARKET_SALE);

    GemResponse response = new GemResponse(buyer.getGem());
    return ResponseEntity.ok(new CommonResponse<>(response));
  }

  @BearerAuth
  @Operation(summary = "상품 생성", description = "마켓에 상품을 등록하는 API")
  @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
  public ResponseEntity<CommonResponse<MarketItemIdResponse>> createMarketItem(
      @Valid @ModelAttribute PostMarketItemRequest request,
      @AuthenticationPrincipal CustomUser user
  ) {
    List<MultipartFile> materialFiles = request.getMaterials();
    List<MaterialType> materialTypes = materialFiles.stream().map(
        MultipartFile::getOriginalFilename).map(MaterialUtil::getMaterialType).toList();
    short materialType = MaterialUtil.getMaterialBitMask(materialTypes);

    Activity activity = activityService.save(request.getAge(), request.getTitle(),
        request.getContent(), request.getCategory(), materialType);
    List<TypedMaterialFile> typedMaterialFiles = TypedMaterialFile.convertTo(materialFiles);
    materialService.saveToS3AndDBWithThumbnails(activity, typedMaterialFiles);

    MarketItem marketItem = marketItemService.save(activity, user.getId(), request.getPrice(),
        request.getYear(), request.getMonth());

    MarketItemIdResponse response = new MarketItemIdResponse(marketItem.getId());
    return ResponseEntity.ok(new CommonResponse<>(response));
  }

  @BearerAuth
  @AuthorizeOwner(MarketItem.class)
  @Operation(summary = "상품 수정", description = "마켓에 상품을 수정하는 API")
  @PutMapping(value = "/{marketItemId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
  public ResponseEntity<CommonResponse<MarketItemIdResponse>> updateMarketItem(
      @Valid @ModelAttribute UpdateMarketItemRequest request,
      @PathVariable("marketItemId") Long marketItemId
  ) {
    MarketItem marketItem = marketItemService.findMarketItemOrThrow(marketItemId);
    Long activityId = marketItem.getActivity().getId();
    List<MultipartFile> materialFiles = request.getMaterials();
    List<Long> deletedMaterialIds = request.getDeletedMaterialIds();
    marketItemService.validateUpdatable(activityId, materialFiles.size(), deletedMaterialIds);

    List<TypedMaterialFile> typedMaterialFiles = TypedMaterialFile.convertTo(materialFiles);
    Activity activity = activityService.findByActivityIdOrThrow(activityId);
    materialService.saveToS3AndDBWithThumbnails(activity, typedMaterialFiles);
    materialService.deleteToS3AndDBWithThumbnails(deletedMaterialIds);
    activityService.update(activity, request.getAge(), request.getTitle(), request.getContent(),
        request.getCategory());
    MarketItem updatedMarketItem = marketItemService.update(marketItem, request.getPrice(),
        request.getYear(), request.getMonth());

    MarketItemIdResponse response = new MarketItemIdResponse(updatedMarketItem.getId());
    return ResponseEntity.ok(new CommonResponse<>(response));
  }

  @BearerAuth
  @AuthorizeOwner(MarketItem.class)
  @Operation(summary = "상품 삭제", description = "마켓의 상품을 삭제하는 API")
  @DeleteMapping("/{marketItemId}")
  public ResponseEntity<EmptyDataResponse> deleteMarketItem(
      @PathVariable("marketItemId") Long marketItemId
  ) {
    marketItemService.delete(marketItemId);
    return ResponseEntity.ok(new EmptyDataResponse());
  }

  @BearerAuth
  @Operation(summary = "스크랩", description = "마켓의 상품을 스크랩하는 API")
  @PostMapping("/{marketItemId}/scrap")
  public ResponseEntity<EmptyDataResponse> scrapMarketItem(
      @PathVariable("marketItemId") Long marketItemId,
      @AuthenticationPrincipal CustomUser user
  ) {
    scrapService.scrap(user.getId(), marketItemId);
    return ResponseEntity.ok(new EmptyDataResponse());
  }

  @BearerAuth
  @Operation(summary = "스크랩 취소", description = "마켓의 상품을 스크랩 취소하는 API")
  @DeleteMapping("/{marketItemId}/scrap")
  public ResponseEntity<EmptyDataResponse> cancelScrapMarketItem(
      @PathVariable("marketItemId") Long marketItemId,
      @AuthenticationPrincipal CustomUser user
  ) {
    scrapService.delete(user.getId(), marketItemId);
    return ResponseEntity.ok(new EmptyDataResponse());
  }

  @Operation(summary = "리뷰 리스트 조회", description = "마켓 상품의 리뷰 리스트를 조회하는 API")
  @GetMapping("/{marketItemId}/reviews")
  public ResponseEntity<CommonResponse<GetReviewsForMarketItemResponse>> getReviewsOfMarketItem(
      @PathVariable("marketItemId") Long marketItemId,
      @RequestParam("order") ReviewOrder order,
      @RequestParam("page") @Min(1) Integer page,
      @AuthenticationPrincipal CustomUser user
  ) {
    Long memberId = CustomUser.getId(user);
    Page<Review> reviews = reviewService.getReviewsForMarketItem(marketItemId, page - 1,
        REVIEW_PAGE_SIZE, order.getSort());
    List<ReviewBundle> reviewBundles = reviewService.convertToReviewBundle(reviews.getContent());
    PageInfo pageInfo = new PageInfo(page, reviews.getTotalPages());
    GetReviewsForMarketItemResponse response = new GetReviewsForMarketItemResponse(reviewBundles,
        pageInfo, memberId);
    return ResponseEntity.ok(new CommonResponse<>(response));
  }

  @BearerAuth
  @AuthorizeOwner(Deal.class)
  @Operation(summary = "리뷰 생성", description = "마켓의 상품에 리뷰를 등록하는 API")
  @PostMapping("/{marketItemId}/review")
  public ResponseEntity<CommonResponse<ReviewResponse>> postReviewToMarketItem(
      @PathVariable("marketItemId") Long marketItemId,
      @Valid @RequestBody PostReviewRequest request,
      @AuthenticationPrincipal CustomUser user
  ) {
    Float score = request.getScore();
    String content = request.getContent();
    Review review = reviewService.saveIfNotExists(marketItemId, user.getId(), score, content);
    ReviewBundle reviewBundle = reviewService.convertToReviewBundle(review);

    ReviewResponse response = new ReviewResponse(reviewBundle);
    return ResponseEntity.ok(new CommonResponse<>(response));
  }

  @Operation(summary = "판매자의 상품 조회", description = "판매자가 마켓에 올린 상품 리스트를 최신순으로 조회하는 API")
  @GetMapping("/seller/{memberId}")
  public ResponseEntity<CommonResponse<GetMarketItemsOfSellerResponse>> getMarketItemsOfSeller(
      @RequestParam("page") @Min(1) Integer page,
      @PathVariable("memberId") Long memberId
  ) {
    Sort newestFirstSort = Order.NEWEST_FIRST.getSort();
    Page<MarketItem> marketItems = marketItemService.getMarketItemsByOwnerOrderBy(memberId, 0,
        MARKET_SELLER_OTHERS_PAGE_SIZE, newestFirstSort);
    List<MarketItemBundle> marketItemBundles =
        marketItemService.convertToMarketItemBundle(marketItems.getContent(), memberId);

    Member owner = memberService.findMemberByMemberIdOrThrow(memberId);
    MemberBundle ownerBundle = memberService.convertToMemberBundle(owner);

    PageInfo pageInfo = new PageInfo(page, marketItems.getTotalPages());
    GetMarketItemsOfSellerResponse response = new GetMarketItemsOfSellerResponse(marketItemBundles,
        ownerBundle, pageInfo);
    return ResponseEntity.ok(new CommonResponse<>(response));
  }

  @BearerAuth
  @Operation(summary = "나의 상품 조회", description = "내가 마켓에 올린 상품 리스트를 최신순으로 조회하는 API")
  @GetMapping("/my")
  public ResponseEntity<CommonResponse<GetMarketItemsResponse>> getMyMarketItems(
      @RequestParam("page") @Min(1) Integer page,
      @AuthenticationPrincipal CustomUser user
  ) {
    Sort newestFirstSort = Order.NEWEST_FIRST.getSort();
    Page<MarketItem> marketItems = marketItemService.getMarketItemsByOwnerOrderBy(user.getId(), 0,
        MARKET_SELLER_OTHERS_PAGE_SIZE, newestFirstSort);
    List<MarketItemBundle> marketItemBundles =
        marketItemService.convertToMarketItemBundle(marketItems.getContent(), user.getId());

    PageInfo pageInfo = new PageInfo(page, marketItems.getTotalPages());
    GetMarketItemsResponse response = new GetMarketItemsResponse(marketItemBundles, pageInfo);
    return ResponseEntity.ok(new CommonResponse<>(response));
  }
}
