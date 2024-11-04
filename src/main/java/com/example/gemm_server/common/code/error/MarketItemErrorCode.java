package com.example.gemm_server.common.code.error;

import com.example.gemm_server.exception.MarketItemException;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum MarketItemErrorCode implements ErrorCode {

  SEARCH_TYPE_NULL(HttpStatus.BAD_REQUEST, "검색 타입을 지정해주세요."),

  MARKET_ITEM_NOT_BELONGS_TO_MEMBER(HttpStatus.UNAUTHORIZED, "사용자의 거래가 아닙니다."),

  MARKET_ITEM_NOT_FOUND(HttpStatus.NOT_FOUND, "존재하지 않는 거래 ID 입니다."),

  INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "서버 에러입니다. 서버 팀에 연락주세요!");

  private final HttpStatus httpStatus;
  private final String message;

  MarketItemErrorCode(HttpStatus httpStatus, String message) {
    this.httpStatus = httpStatus;
    this.message = message;
  }

  @Override
  public MarketItemException getException() {
    return new MarketItemException(this);
  }

  @Override
  public MarketItemException getException(Throwable cause) {
    return new MarketItemException(this, cause);
  }
}