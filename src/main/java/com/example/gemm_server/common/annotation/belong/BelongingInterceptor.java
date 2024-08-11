package com.example.gemm_server.common.annotation.belong;

import static com.example.gemm_server.common.code.error.GenerationErrorCode.GENERATION_NOT_BELONGS_TO_MEMBER;
import static com.example.gemm_server.common.code.error.GenerationErrorCode.GENERATION_NOT_FOUND;

import com.example.gemm_server.domain.entity.Generation;
import com.example.gemm_server.domain.repository.GenerationRepository;
import com.example.gemm_server.dto.ErrorResponse;
import com.example.gemm_server.exception.GenerationException;
import com.example.gemm_server.security.jwt.CustomUser;
import io.micrometer.common.lang.NonNull;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.HandlerMapping;

@RequiredArgsConstructor
@Component
public class BelongingInterceptor implements HandlerInterceptor {

  private final GenerationRepository generationRepository;
  private HttpServletRequest request;

  @Override
  public boolean preHandle(
      @NonNull HttpServletRequest request,
      @NonNull HttpServletResponse response,
      @NonNull Object handler
  ) throws Exception {
    if (!(handler instanceof HandlerMethod handlerMethod)) {
      return true;
    }
    this.request = request;

    try {
      interceptGeneration(handlerMethod, request);
      return true;
    } catch (GenerationException e) {
      ErrorResponse.setJsonResponse(response, e.getStatusCode(), e.getMessage());
      return false;
    } catch (NumberFormatException e) {
      ErrorResponse.setJsonResponse(response, 400, e.getMessage());
      return false;
    }
  }

  private void interceptGeneration(HandlerMethod handlerMethod, HttpServletRequest request) {

    GenerationBelonging generationBelonging =
        handlerMethod.getMethodAnnotation(GenerationBelonging.class);
    if (generationBelonging == null) {
      return;
    }

    Long memberId = getLoggedInMemberId();
    Long generationId = getIdFromUrl("generationId");

    Generation generation = generationRepository.findById(generationId)
        .orElseThrow(() -> new GenerationException(GENERATION_NOT_FOUND));
    if (!generation.getOwner().getId().equals(memberId)) {
      throw new GenerationException(GENERATION_NOT_BELONGS_TO_MEMBER);
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
    Map<String, String> pathVariables =
        (Map<String, String>) request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
    return Long.parseLong(pathVariables.get(name));
  }
}
