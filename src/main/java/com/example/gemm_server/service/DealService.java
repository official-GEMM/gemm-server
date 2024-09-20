package com.example.gemm_server.service;

import static com.example.gemm_server.common.code.error.DealErrorCode.DEAL_NOT_FOUND;

import com.example.gemm_server.domain.entity.Deal;
import com.example.gemm_server.domain.entity.Thumbnail;
import com.example.gemm_server.domain.repository.DealRepository;
import com.example.gemm_server.dto.storage.DealBundle;
import com.example.gemm_server.exception.DealException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DealService {

  private final DealRepository dealRepository;
  private final ThumbnailService thumbnailService;

  public Page<Deal> getDealsByMemberIdAndPage(Long memberId, int pageNumber, int pageSize) {
    Sort sort = Sort.by(Direction.DESC, "createdAt");
    Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
    return dealRepository.findWithActivityByBuyerId(memberId, pageable);
  }

  public Deal getDealWithActivityOrThrow(Long dealId) {
    return dealRepository.findWithActivityById(dealId)
        .orElseThrow(() -> new DealException(DEAL_NOT_FOUND));
  }

  public DealBundle convertToDealBundle(Deal deal) {
    Thumbnail thumbnail = thumbnailService.getMainThumbnail(deal.getActivity().getId());
    return new DealBundle(deal, thumbnail);
  }

  public List<DealBundle> convertToDealBundle(List<Deal> deals) {
    return deals.stream().map(this::convertToDealBundle).toList();
  }
}
