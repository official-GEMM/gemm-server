package com.example.gemm_server.common.annotation.auth;

import static com.example.gemm_server.common.code.error.DealErrorCode.DEAL_NOT_BELONGS_TO_MEMBER;
import static com.example.gemm_server.common.code.error.DealErrorCode.DEAL_NOT_FOUND;
import static com.example.gemm_server.common.code.error.GenerationErrorCode.GENERATION_NOT_BELONGS_TO_MEMBER;
import static com.example.gemm_server.common.code.error.GenerationErrorCode.GENERATION_NOT_FOUND;
import static com.example.gemm_server.common.code.error.MarketItemErrorCode.MARKET_ITEM_NOT_BELONGS_TO_MEMBER;
import static com.example.gemm_server.common.code.error.MarketItemErrorCode.MARKET_ITEM_NOT_FOUND;
import static com.example.gemm_server.common.code.error.MarketItemErrorCode.MARKET_ITEM_NOT_PURCHASED;

import com.example.gemm_server.domain.entity.Deal;
import com.example.gemm_server.domain.entity.Generation;
import com.example.gemm_server.domain.entity.MarketItem;
import com.example.gemm_server.domain.repository.DealRepository;
import com.example.gemm_server.domain.repository.GenerationRepository;
import com.example.gemm_server.domain.repository.MarketItemRepository;
import com.example.gemm_server.dto.ErrorResponse;
import com.example.gemm_server.exception.DealException;
import com.example.gemm_server.exception.GenerationException;
import com.example.gemm_server.exception.MarketItemException;
import com.example.gemm_server.security.jwt.CustomUser;
import io.micrometer.common.lang.NonNull;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.Map;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.HandlerMapping;

@RequiredArgsConstructor
@Component
public class AuthorizeOwnerInterceptor implements HandlerInterceptor {

  private final GenerationRepository generationRepository;
  private final MarketItemRepository marketItemRepository;
  private final DealRepository dealRepository;

  private HttpServletRequest request;

  @Override
  public boolean preHandle(
      @NonNull HttpServletRequest request,
      @NonNull HttpServletResponse response,
      @NonNull Object handler
  ) throws Exception {
    this.request = request;
    if (!(handler instanceof HandlerMethod handlerMethod)) {
      return true;
    }
    AuthorizeOwner authorizeOwner = handlerMethod.getMethodAnnotation(AuthorizeOwner.class);
    if (authorizeOwner == null) {
      return true;
    }

    try {
      Long memberId = getLoggedInMemberId();
      Class<?> entityClass = authorizeOwner.value();
      if (entityClass == Generation.class) {
        checkGenerationOwner(memberId);
      }
      if (entityClass == Deal.class) {
        checkDealOwner(memberId);
      }
      if (entityClass == MarketItem.class) {
        checkMarketItemOwner(memberId);
      }

      return true;
    } catch (GenerationException | DealException | MarketItemException e) {
      ErrorResponse.setJsonResponse(response, e.getStatusCode(), e.getMessage());
    } catch (NumberFormatException e) {
      ErrorResponse.setJsonResponse(response, 400, e.getMessage());
    }
    return false;
  }

  private void checkGenerationOwner(Long memberId) {
    Long generationId = getIdFromUrl("generationId");

    Generation generation = generationRepository.findById(generationId)
        .orElseThrow(() -> new GenerationException(GENERATION_NOT_FOUND));
    if (!generation.getOwner().getId().equals(memberId)) {
      throw new GenerationException(GENERATION_NOT_BELONGS_TO_MEMBER);
    }
  }

  private void checkDealOwner(Long memberId) {
    Long dealId = getIdFromUrl("dealId");
    Long marketItemId = getIdFromUrl("marketItemId");

    if (dealId != null) {
      Deal deal = dealRepository.findById(dealId)
          .orElseThrow(() -> new DealException(DEAL_NOT_FOUND));
      if (!deal.getBuyer().getId().equals(memberId)) {
        throw new DealException(DEAL_NOT_BELONGS_TO_MEMBER);
      }
    }

    if (marketItemId != null) {
      MarketItem marketItem = marketItemRepository.findWithActivityById(marketItemId)
          .orElseThrow(() -> new MarketItemException(MARKET_ITEM_NOT_FOUND));
      Long activityId = marketItem.getActivity().getId();
      if (!dealRepository.existsByActivityIdAndBuyerId(activityId, memberId)) {
        throw new MarketItemException(MARKET_ITEM_NOT_PURCHASED);
      }
    }
  }

  private void checkMarketItemOwner(Long memberId) {
    Long marketItemId = getIdFromUrl("marketItemId");

    MarketItem marketItem = marketItemRepository.findById(marketItemId)
        .orElseThrow(() -> new MarketItemException(MARKET_ITEM_NOT_FOUND));
    if (!marketItem.getOwner().getId().equals(memberId)) {
      throw new MarketItemException(MARKET_ITEM_NOT_BELONGS_TO_MEMBER);
    }
  }

  private Long getLoggedInMemberId() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    if (authentication != null && authentication.isAuthenticated()) {
      Object principal = authentication.getPrincipal();

      if (principal instanceof CustomUser) {
        return ((CustomUser) principal).getId();
      }
    }
    return null;
  }

  private Long getIdFromUrl(String name) {
    @SuppressWarnings("unchecked")
    Map<String, String> pathVariables =
        (Map<String, String>) request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
    return Optional.ofNullable(pathVariables.get(name))
        .map(Long::parseLong)
        .orElse(null);
  }
}
