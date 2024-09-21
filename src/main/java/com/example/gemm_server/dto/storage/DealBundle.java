package com.example.gemm_server.dto.storage;

import com.example.gemm_server.domain.entity.Deal;
import com.example.gemm_server.domain.entity.Thumbnail;
import com.example.gemm_server.dto.common.ActivityBundle;
import lombok.Getter;

@Getter
public class DealBundle extends ActivityBundle {

  private Deal deal;

  public DealBundle(Deal dealWithActivity, Thumbnail thumbnail) {
    super(dealWithActivity.getActivity(), thumbnail);
    this.deal = dealWithActivity;
  }
}
