package com.example.gemm_server.dto.auth;

import com.example.gemm_server.domain.entity.Member;

public record MemberCompensation(boolean isCompensated, Member member) {

}
