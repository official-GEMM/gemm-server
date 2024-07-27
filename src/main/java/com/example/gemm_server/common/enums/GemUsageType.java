package com.example.gemm_server.common.enums;

import lombok.Getter;

public enum GemUsageType {
  COMPENSATION("보상", Transaction.DEPOSIT),
  CHARGE("충전", Transaction.DEPOSIT),
  MARKET_SALE("마켓 판매", Transaction.DEPOSIT),
  MARKET_PURCHASE("마켓 구매", Transaction.WITHDRAWAL),
  AI_USE("AI 사용", Transaction.WITHDRAWAL);

  @Getter
  private final String description;
  private final Transaction transaction;

  GemUsageType(String description, Transaction transaction) {
    this.description = description;
    this.transaction = transaction;
  }

  private enum Transaction {
    DEPOSIT(1),
    WITHDRAWAL(-1);

    private final int sign;

    Transaction(int sign) {
      this.sign = sign;
    }
  }

  public int getSignedAmount(int amount) {
    return amount * this.transaction.sign;
  }
}
