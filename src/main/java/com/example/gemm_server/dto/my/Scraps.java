package com.example.gemm_server.dto.my;

import com.example.gemm_server.common.enums.Category;
import com.example.gemm_server.common.enums.MaterialType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import org.springframework.core.io.UrlResource;

@Getter
public class Scraps {

  @Schema(description = "스크랩 아이디")
  private long scrapId;

  @Schema(description = "제목")
  private String title;

  @Schema(description = "썸네일 경로")
  private UrlResource thumbnailPath;

  @Schema(description = "판매자")
  private Seller seller;

  @Schema(description = "리뷰 평점")
  private float reviewAverageScore;

  @Schema(description = "리뷰 수")
  private Long reviewCount;

  @Schema(description = "가격")
  private Long price;

  @Schema(description = "타켓 연령")
  private Long age;

  @Schema(description = "영역 및 활동")
  private Category category;

  @Schema(description = "자료 종류")
  private MaterialType[] materialType;

  @Getter
  private static class Seller {

    @Schema(description = "사용자 아이디")
    private long memberId;

    @Schema(description = "사용자 닉네임")
    private String nickname;
  }

  public Scraps() {
  }
}