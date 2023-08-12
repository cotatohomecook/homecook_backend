package com.cotato.homecook.config.security;

import com.cotato.homecook.domain.dto.auth.UserDto;
import com.cotato.homecook.exception.AppException;
import com.cotato.homecook.exception.ErrorCode;
import io.jsonwebtoken.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@RequiredArgsConstructor
@Component
public class JwtUtils {
    @Value("${jwt.access-token-time}")
    private long accessTokenTime;
    @Value("${jwt.refresh-token-time}")
    private long refreshTokenTime;
    @Value("${jwt.secret}")
    private String jwtSecretKey;
    private final StringRedisTemplate stringRedisTemplate;
    public String createAccessToken(UserDto userDto, String role) {
        Claims claims = Jwts.claims();
        claims.put("email", userDto.getEmail());
        claims.put("username", userDto.getUsername());
        claims.put("role", role);
        long validTime = accessTokenTime;
        Date now = new Date();
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + validTime))
                .signWith(SignatureAlgorithm.HS256, jwtSecretKey)
                .compact();
    }


    public String createRefreshToken(UserDto userDto){
        Claims claims = Jwts.claims();
        claims.put("email", userDto.getEmail());
        claims.put("username", userDto.getUsername());
        long validTime = refreshTokenTime;
        Date now = new Date();
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + validTime))
                .signWith(SignatureAlgorithm.HS256, jwtSecretKey)
                .compact();
    }

    public void updateUserRefreshToken(UserDto userDto, String refreshToken) {
        stringRedisTemplate.opsForValue().set(userDto.getEmail(), refreshToken);
    }

    public String getUserRefreshToken(String email) {
        return stringRedisTemplate.opsForValue().get(email);
    }

    public boolean validateToken(String token) {
        if (!StringUtils.hasText(token)) {
            throw new AppException(ErrorCode.JWT_TOKEN_NOT_EXISTS);
        }
        try {
            Claims claims = Jwts.parser().setSigningKey(jwtSecretKey).parseClaimsJws(token).getBody();
            return true;
        } catch (SignatureException | MalformedJwtException e) {
            throw new AppException(ErrorCode.WRONG_JWT_TOKEN);
        } catch (ExpiredJwtException e) {
            throw new AppException(ErrorCode.JWT_TOKEN_EXPIRED);
        }
    }

    public String getEmailFromToken(String token) {
        return (String) getClaims(token).get("email");
    }

    public String getRoleFromToken(String token) {
        return (String) getClaims(token).get("role");
    }

    public Claims getClaims(String token) {
        return Jwts.parser().setSigningKey(jwtSecretKey).parseClaimsJws(token).getBody();
    }

    public String resolveAccessToken(HttpServletRequest request) {
        String jwtHeader = request.getHeader("Authorization");
        if (jwtHeader != null && jwtHeader.startsWith("Bearer ")) {
            return jwtHeader.replace("Bearer ", "");
        } else {
            return null;
        }
    }
}