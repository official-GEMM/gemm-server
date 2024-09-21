package com.example.gemm_server.service;

import com.example.gemm_server.domain.entity.Banner;
import com.example.gemm_server.domain.repository.BannerRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BannerService {

  private final BannerRepository bannerRepository;

  public List<Banner> getBanners() {
    return bannerRepository.findAll();
  }
}
