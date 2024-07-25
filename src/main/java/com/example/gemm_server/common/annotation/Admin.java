package com.example.gemm_server.common.annotation;

import com.example.gemm_server.common.code.error.MemberErrorCode;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(value = {ElementType.METHOD, ElementType.TYPE})
@Retention(value = RetentionPolicy.RUNTIME)
public @interface Admin {

  MemberErrorCode errorCode() default MemberErrorCode.LACK_OF_AUTHORITY;
}
