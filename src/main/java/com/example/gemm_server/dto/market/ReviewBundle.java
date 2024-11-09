package com.example.gemm_server.dto.market;

import com.example.gemm_server.domain.entity.Review;
import com.example.gemm_server.dto.common.MemberBundle;
import lombok.Getter;

@Getter
public class ReviewBundle {

  private Review review;
  private MemberBundle writer;

  public ReviewBundle(Review review, MemberBundle writer) {
    this.review = review;
    this.writer = writer;
  }
}
