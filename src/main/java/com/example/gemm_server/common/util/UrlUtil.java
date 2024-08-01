package com.example.gemm_server.common.util;

import java.net.MalformedURLException;
import org.springframework.core.io.UrlResource;

public class UrlUtil {

  public static UrlResource createUrlResource(String url) {
    try {
      return new UrlResource(url);
    } catch (MalformedURLException e) {
      throw new RuntimeException(e);
    }
  }
}
