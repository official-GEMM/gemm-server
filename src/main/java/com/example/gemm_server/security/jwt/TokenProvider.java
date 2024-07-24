package com.example.gemm_server.security.jwt;

import static com.example.gemm_server.common.code.error.TokenErrorCode.EXPIRED_JWT_TOKEN;
import static com.example.gemm_server.common.code.error.TokenErrorCode.INVALID_JWT_SIGNATURE;
import static com.example.gemm_server.common.code.error.TokenErrorCode.INVALID_JWT_TOKEN;
import static com.example.gemm_server.common.code.error.TokenErrorCode.UNSUPPORTED_JWT_TOKEN;

import com.example.gemm_server.exception.TokenException;
import com.example.gemm_server.service.TokenService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import javax.crypto.SecretKey;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@RequiredArgsConstructor
@Component
public class TokenProvider {

  private final TokenService tokenService;
  @Value("${jwt.key}")
  private String key;
  @Value("${jwt.access-token-expire-time}")
  private long accessTokenExpireTime;
  @Value("${jwt.refresh-token-expire-time}")
  private long refreshTokenExpireTime;
  private SecretKey secretKey;
  private static final String AUTHORITIES_KEY = "role";

  @PostConstruct
  public void setSecretKey() {
    this.secretKey = Keys.hmacShaKeyFor(key.getBytes());
  }

  public String generateAccessToken(Authentication authentication) {
    return generateToken(authentication, accessTokenExpireTime);
  }

  // 1. refresh token 발급
  public String generateRefreshToken(Authentication authentication) {
    String refreshToken = generateToken(authentication, refreshTokenExpireTime);
    tokenService.updateRefreshToken(Long.parseLong(authentication.getName()), refreshToken);
    return refreshToken;
  }

  private String generateToken(Authentication authentication, long expireTime) {
    Date now = new Date();
    Date expiredDate = new Date(now.getTime() + expireTime);

    String authorities = authentication.getAuthorities().stream()
        .map(GrantedAuthority::getAuthority)
        .collect(Collectors.joining());

    return Jwts.builder()
        .setSubject(authentication.getName())
        .claim(AUTHORITIES_KEY, authorities)
        .setIssuedAt(now)
        .setExpiration(expiredDate)
        .signWith(secretKey, SignatureAlgorithm.HS512)
        .compact();
  }

  public Authentication getAuthentication(String token) {
    Claims claims = parseClaims(token);
    List<SimpleGrantedAuthority> authorities = getAuthorities(claims);

    // 2. security의 User 객체 생성
    User principal = new User(claims.getSubject(), "", authorities);
    return new UsernamePasswordAuthenticationToken(principal, token, authorities);
  }

  public Long getUserIdFromToken(String token) {
    Claims claims = parseClaims(token);
    return Long.parseLong(claims.getSubject());
  }

  private List<SimpleGrantedAuthority> getAuthorities(Claims claims) {
    return Collections.singletonList(new SimpleGrantedAuthority(
        claims.get(AUTHORITIES_KEY).toString()));
  }

  // 3. accessToken 재발급
  public String reissueAccessToken(String accessToken) {
    if (StringUtils.hasText(accessToken)) {
      Long memberId = Long.parseLong(getAuthentication(accessToken).getName());
      String refreshToken = tokenService.getRefreshToken(memberId);

      if (validateToken(refreshToken)) {
        return generateAccessToken(getAuthentication(refreshToken));
      }
    }
    return null;
  }

  public boolean validateToken(String token) {
    if (!StringUtils.hasText(token)) {
      return false;
    }
    Claims claims = parseClaims(token);
    return claims.getExpiration().after(new Date());
  }

  private Claims parseClaims(String token) {
    try {
      return Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(token).getBody();
    } catch (SecurityException | MalformedJwtException e) {
      throw new TokenException(INVALID_JWT_SIGNATURE);
    } catch (ExpiredJwtException e) {
      throw new TokenException(EXPIRED_JWT_TOKEN);
    } catch (UnsupportedJwtException e) {
      throw new TokenException(UNSUPPORTED_JWT_TOKEN);
    } catch (IllegalArgumentException e) {
      throw new TokenException(INVALID_JWT_TOKEN);
    }
  }
}