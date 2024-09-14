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
  public static final int STORAGE_ACTIVITY_LIMIT = 9;
  public static final int STORAGE_GUIDE_LIMIT = 6;

  // 휴대전화 인증 시도 가능 횟수
  public static final int VERIFICATION_LIMIT = 5;
}
