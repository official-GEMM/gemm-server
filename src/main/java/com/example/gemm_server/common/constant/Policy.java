package com.example.gemm_server.common.constant;

public class Policy {

  // 로그인 및 회원가입 잼
  public static final int ATTENDANCE_COMPENSATION = 30;
  public static final int JOIN_COMPENSATION = 1000;
  public static final int REFERRAL_COMPENSATION = 500;

  // 방법 및 자료 생성 잼
  public static final int GENERATE_GUIDE = 10;
  public static final int UPDATE_GUIDE = 10;
  public static final int GENERATE_PPT = 20;
  public static final int GENERATE_ACTIVITY_SHEET = 15;
  public static final int GENERATE_CUTOUT = 15;
  public static final int UPDATE_PPT = 20;
  public static final int UPDATE_ACTIVITY_SHEET = 15;
  public static final int UPDATE_CUTOUT = 15;

  // 페이지 당 요소 개수
  public static final int MARKET_SEARCH_PAGE_SIZE = 12;
  public static final int MARKET_SELLER_OTHERS_PAGE_SIZE = 12;
  public static final int STORAGE_ACTIVITY_PAGE_SIZE = 9;
  public static final int STORAGE_GUIDE_PAGE_SIZE = 6;
  public static final int MAIN_MOST_SCRAPPED_PAGE_SIZE = 8;
  public static final int MAIN_RECOMMENDED_PAGE_SIZE = 8;
  public static final int REVIEW_PAGE_SIZE = 5;
  public static final int SCRAP_PAGE_SIZE = 9;
  public static final int DEAL_PAGE_SIZE = 4;
  public static final int ANALYTICS_PAGE_SIZE = 10;

  // 휴대전화 인증 시도 가능 횟수
  public static final int VERIFICATION_LIMIT = 5;
  public static final int VERIFICATION_SMS_SEND_LIMIT = 5;

  // 마켓 상품당 최대 자료 등록 개수
  public static final int MATERIAL_MAX_COUNT_PER_MARKET_ITEM = 5;
}
