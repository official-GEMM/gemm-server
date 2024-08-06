package com.example.gemm_server.domain.repository;

import com.example.gemm_server.domain.entity.Thumbnail;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ThumbnailRepository extends JpaRepository<Thumbnail, Long> {

}
