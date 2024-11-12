package com.example.gemm_server.common.code.error;

import com.example.gemm_server.exception.MarketItemException;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum MarketItemErrorCode implements ErrorCode {

  SEARCH_TYPE_NULL(HttpStatus.BAD_REQUEST, "검색 타입을 지정해주세요."),
  CANNOT_PURCHASE_OWN_MARKET_ITEM(HttpStatus.BAD_REQUEST, "본인의 마켓 상품을 구매할 수 없습니다."),
  MARKET_ITEM_ALREADY_REVIEWED(HttpStatus.BAD_REQUEST, "이미 리뷰가 작성된 상품입니다."),
  MARKET_ITEM_HAS_DEAL(HttpStatus.BAD_REQUEST, "구매된 적이 있는 상품은 수정이 불가합니다."),
  EXCEED_MAX_MATERIAL_COUNT(HttpStatus.BAD_REQUEST, "마켓 상품 당 자료는 최대 5개까지 등록 가능합니다."),

  MARKET_ITEM_NOT_BELONGS_TO_MEMBER(HttpStatus.UNAUTHORIZED, "사용자가 보유한 상품이 아닙니다."),
  MARKET_ITEM_NOT_PURCHASED(HttpStatus.UNAUTHORIZED, "사용자가 구매한 상품이 아닙니다."),

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