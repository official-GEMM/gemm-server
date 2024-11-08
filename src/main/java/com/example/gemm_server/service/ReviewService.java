package com.example.gemm_server.service;

import static com.example.gemm_server.common.code.error.MarketItemErrorCode.MARKET_ITEM_ALREADY_REVIEWED;

import com.example.gemm_server.domain.entity.Review;
import com.example.gemm_server.domain.repository.ReviewRepository;
import com.example.gemm_server.dto.common.MemberBundle;
import com.example.gemm_server.dto.market.ReviewBundle;
import com.example.gemm_server.exception.MarketItemException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReviewService {

  private final ReviewRepository reviewRepository;
  private final MemberService memberService;

  public ReviewBundle convertToReviewBundle(Review reviewWithWriter) {
    MemberBundle writer = memberService.convertToMemberBundle(reviewWithWriter.getMember());
    return new ReviewBundle(reviewWithWriter, writer);

  }

  public List<ReviewBundle> convertToReviewBundle(List<Review> reviewsWithWriter) {
    return reviewsWithWriter.stream().map(this::convertToReviewBundle).toList();
  }

  public Review saveIfNotExists(Long marketItemId, Long memberId, Float score, String content) {
    if (reviewRepository.existsByMarketItemIdAndMemberId(marketItemId, memberId)) {
      throw new MarketItemException(MARKET_ITEM_ALREADY_REVIEWED);
    }
    Review review = Review.builder()
        .marketItemId(marketItemId)
        .memberId(memberId)
        .score(score)
        .content(content)
        .build();
    return reviewRepository.save(review);
  }
}
