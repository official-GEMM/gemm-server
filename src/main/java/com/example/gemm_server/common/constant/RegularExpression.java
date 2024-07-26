package com.example.gemm_server.common.constant;

public class RegularExpression {

  public static final String NAME = "^[가-힣a-zA-Z]{2,30}$";
  public static final String NICKNAME = "^[가-힣]{2,12}$";
  public static final String PHONE_NUMBER = "^01[016789]-\\d{3,4}-\\d{4}$";
  public static final String DATE = "^\\d{4}-(0[1-9]|1[0-2])-(0[1-9]|[12]\\d|3[01])$";
}
