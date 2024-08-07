package com.example.gemm_server.common.constant;

import java.time.LocalDateTime;

public class NotificationMessage {

  private static final int MAX_LENGTH = 8;

  public static String purchaseMarketItem(String buyerNickname, String itemName, int gem) {
    return truncateName(buyerNickname) + "님이 " + truncateName(itemName) + "를 " + gem
        + "젬에 구매하였습니다.";
  }

  public static String chargeGemByReferral(int gem) {
    return "추천인으로 등록되어 " + gem + "젬이 지급되었습니다.";
  }

  public static String chargeGemByReview(String title, int gem) {
    return title + "에 리뷰를 달아 " + gem + "젬이 지급되었습니다.";
  }

  public static String chargeGemByAttendance(LocalDateTime today, int gem) {
    return today + "에 출석하여 " + gem + "젬이 지급되었습니다.";
  }

  private static String truncateName(String name) {
    if (name.length() <= MAX_LENGTH) {
      return name;
    }
    return name.substring(0, MAX_LENGTH - 3) + "...";
  }
}
