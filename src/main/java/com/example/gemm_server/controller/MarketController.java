package com.example.gemm_server.controller;

import static com.example.gemm_server.common.constant.Policy.MAIN_MOST_SCRAPPED_PAGE_SIZE;
import static com.example.gemm_server.common.constant.Policy.MAIN_RECOMMENDED_PAGE_SIZE;

import com.example.gemm_server.common.annotation.auth.AuthorizeOwner;
import com.example.gemm_server.common.annotation.auth.BearerAuth;
import com.example.gemm_server.common.enums.ReviewOrder;
import com.example.gemm_server.domain.entity.Banner;
import com.example.gemm_server.domain.entity.MarketItem;
import com.example.gemm_server.dto.CommonResponse;
import com.example.gemm_server.dto.EmptyDataResponse;
import com.example.gemm_server.dto.common.response.DownloadMaterialResponse;
import com.example.gemm_server.dto.common.response.GemResponse;
import com.example.gemm_server.dto.market.MarketItemBundle;
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
import com.example.gemm_server.service.BannerService;
import com.example.gemm_server.service.MarketItemService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.websocket.server.PathParam;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RequiredArgsConstructor
@RestController()
@RequestMapping("/market")
@Tag(name = "Maret", description = "마켓 API")
public class MarketController {

  private final MarketItemService marketItemService;
  private final BannerService bannerService;

  // 미완성 API
  @Operation(summary = "메인", description = "메인 페이지에 필요한 정보를 가져오는 API")
  @GetMapping("/main")
  public ResponseEntity<CommonResponse<GetMainResponse>> getMain(
      @AuthenticationPrincipal CustomUser user
  ) {
    Long memberId = CustomUser.getId(user);
    List<MarketItem> recommendedMarketItems = marketItemService.getMarketItemsOrderByRecommendation(
        0, MAIN_RECOMMENDED_PAGE_SIZE).getContent();
    List<MarketItem> mostScrappedMarketItems = marketItemService.getMarketItemsOrderByScrapCountDesc(
        0, MAIN_MOST_SCRAPPED_PAGE_SIZE).getContent();

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
      @Valid @ModelAttribute SearchQueryRequest request
  ) {
    GetMarketItemsResponse response = new GetMarketItemsResponse();
    return ResponseEntity.ok(new CommonResponse<>(response));
  }

  @Operation(summary = "상품 상세", description = "상품의 상세 정보를 조회하는 API")
  @GetMapping("/{marketItemId}")
  public ResponseEntity<CommonResponse<MarketItemDetailResponse>> getMarketItemDetail(
      @PathParam("marketItemId") Long marketItemId
  ) {
    MarketItemDetailResponse response = new MarketItemDetailResponse();
    return ResponseEntity.ok(new CommonResponse<>(response));
  }

  @Operation(summary = "판매자의 다른 상품", description = "판매자의 다른 상품을 추천순으로 조회하는 API")
  @GetMapping("/{marketItemId}/others")
  public ResponseEntity<CommonResponse<GetOtherMarketItemsOfSellerResponse>> getOtherMarketItmesOfSeller(
      @PathParam("marketItemId") Long marketItemId
  ) {
    GetOtherMarketItemsOfSellerResponse response = new GetOtherMarketItemsOfSellerResponse();
    return ResponseEntity.ok(new CommonResponse<>(response));
  }

  @BearerAuth
  @Operation(summary = "자료 다운로드", description = "상품의 활동 자료들을 다운로드하는 API")
  @GetMapping("/{marketItemId}/download")
  public ResponseEntity<CommonResponse<DownloadMaterialResponse>> downloadMaterials(
      @PathParam("marketItemId") Long marketItemId
  ) {
    DownloadMaterialResponse response = new DownloadMaterialResponse();
    return ResponseEntity.ok(new CommonResponse<>(response));
  }

  @BearerAuth
  @Operation(summary = "상품 구매", description = "마켓의 상품을 구매하는 API")
  @PostMapping("/{marketItemId}/buy")
  public ResponseEntity<CommonResponse<GemResponse>> buyMarketItem(
      @PathParam("marketItemId") Long marketItemId
  ) {
    GemResponse response = new GemResponse(0);
    return ResponseEntity.ok(new CommonResponse<>(response));
  }

  @BearerAuth
  @Operation(summary = "상품 생성", description = "마켓에 상품을 등록하는 API")
  @PostMapping()
  public ResponseEntity<CommonResponse<MarketItemIdResponse>> createMarketItem(
      @Valid @RequestBody PostMarketItemRequest request
  ) {
    MarketItemIdResponse response = new MarketItemIdResponse();
    return ResponseEntity.ok(new CommonResponse<>(response));
  }

  @BearerAuth
  @AuthorizeOwner(MarketItem.class)
  @Operation(summary = "상품 수정", description = "마켓에 상품을 수정하는 API")
  @PutMapping("/{marketItemId}")
  public ResponseEntity<CommonResponse<MarketItemIdResponse>> updateMarketItem(
      @Valid @RequestBody UpdateMarketItemRequest request,
      @PathParam("marketItemId") Long marketItemId
  ) {
    MarketItemIdResponse response = new MarketItemIdResponse();
    return ResponseEntity.ok(new CommonResponse<>(response));
  }

  @BearerAuth
  @AuthorizeOwner(MarketItem.class)
  @Operation(summary = "상품 삭제", description = "마켓의 상품을 삭제하는 API")
  @DeleteMapping("/{marketItemId}")
  public ResponseEntity<EmptyDataResponse> deleteMarketItem(
      @PathParam("marketItemId") Long marketItemId
  ) {
    return ResponseEntity.ok(new EmptyDataResponse());
  }

  @BearerAuth
  @Operation(summary = "스크랩", description = "마켓의 상품을 스크랩하는 API")
  @PostMapping("/{marketItemId}/scrap")
  public ResponseEntity<EmptyDataResponse> scrapMarketItem(
      @PathParam("marketItemId") Long marketItemId
  ) {
    return ResponseEntity.ok(new EmptyDataResponse());
  }

  @BearerAuth
  @Operation(summary = "스크랩 취소", description = "마켓의 상품을 스크랩 취소하는 API")
  @DeleteMapping("/{marketItemId}/scrap")
  public ResponseEntity<EmptyDataResponse> cancelScrapMarketItem(
      @PathParam("marketItemId") Long marketItemId
  ) {
    return ResponseEntity.ok(new EmptyDataResponse());
  }

  @Operation(summary = "리뷰 리스트 조회", description = "마켓 상품의 리뷰 리스트를 조회하는 API")
  @GetMapping("/{marketItemId}/reviews")
  public ResponseEntity<CommonResponse<GetReviewsForMarketItemResponse>> getReviewsOfMarketItem(
      @PathParam("marketItemId") Long marketItemId,
      @RequestParam("order") ReviewOrder order,
      @RequestParam("page") @Min(1) Integer page
  ) {
    GetReviewsForMarketItemResponse response = new GetReviewsForMarketItemResponse();
    return ResponseEntity.ok(new CommonResponse<>(response));
  }

  @BearerAuth
  @Operation(summary = "리뷰 생성", description = "마켓의 상품에 리뷰를 등록하는 API")
  @PostMapping("/{marketItemId}/review")
  public ResponseEntity<CommonResponse<GetReviewsForMarketItemResponse>> postReviewToMarketItem(
      @PathParam("marketItemId") Long marketItemId,
      @Valid @RequestBody PostReviewRequest request
  ) {
    GetReviewsForMarketItemResponse response = new GetReviewsForMarketItemResponse();
    return ResponseEntity.ok(new CommonResponse<>(response));
  }

  @Operation(summary = "판매자의 상품 조회", description = "판매자가 마켓에 올린 상품 리스트를 최신순으로 조회하는 API")
  @GetMapping("/seller/{memberId}")
  public ResponseEntity<CommonResponse<GetMarketItemsOfSellerResponse>> getMarketItemsOfSeller(
      @RequestParam("page") @Min(1) Integer page,
      @PathParam("memberId") Long memberId
  ) {
    GetMarketItemsOfSellerResponse response = new GetMarketItemsOfSellerResponse();
    return ResponseEntity.ok(new CommonResponse<>(response));
  }

  @BearerAuth
  @Operation(summary = "나의 상품 조회", description = "내가 마켓에 올린 상품 리스트를 최신순으로 조회하는 API")
  @GetMapping("/my")
  public ResponseEntity<CommonResponse<GetMarketItemsResponse>> getMyMarketItems(
      @RequestParam("page") @Min(1) Integer page
  ) {
    GetMarketItemsResponse response = new GetMarketItemsResponse();
    return ResponseEntity.ok(new CommonResponse<>(response));
  }
}
