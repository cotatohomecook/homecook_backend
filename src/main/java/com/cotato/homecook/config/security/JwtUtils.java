package com.cotato.homecook.config.security;

import com.cotato.homecook.domain.dto.auth.UserDto;
import com.cotato.homecook.exception.AppException;
import com.cotato.homecook.exception.ErrorCode;
import io.jsonwebtoken.*;
import lombok.RequiredArgsConstructor;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@RequiredArgsConstructor
public class JwtUtils {
    private static final long accessTokenValidTime = 60 * 60 * 1000L; // 10초
    private static final long refreshTokenValidTime = 30 * 60 * 1000L; // 30분

    public static String createToken(UserDto userDto, String type, String jwtSecretKey) {
        Claims claims = Jwts.claims();
        claims.put("email", userDto.getEmail());
        claims.put("username", userDto.getUsername());
        claims.put("role", userDto.getRole());
        Date now = new Date();
        long validTime = type.equals("access") ? accessTokenValidTime : refreshTokenValidTime;
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + validTime))
                .signWith(SignatureAlgorithm.HS256, jwtSecretKey)
                .compact();
    }

    public static boolean validateToken(String token, String jwtSecretKey) {
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

    public static String getEmailFromToken(String token, String jwtSecretKey) {
        return (String) getClaims(token, jwtSecretKey).get("email");
    }

    public static String getRoleFromToken(String token, String jwtSecretKey) {
        return (String) getClaims(token, jwtSecretKey).get("role");
    }

    public static Claims getClaims(String token, String jwtSecretKey) {
        return Jwts.parser().setSigningKey(jwtSecretKey).parseClaimsJws(token).getBody();
    }

    public static String resolveAccessToken(HttpServletRequest request) {
        String jwtHeader = request.getHeader("Authorization");
        if (jwtHeader != null && jwtHeader.startsWith("Bearer ")) {
            return jwtHeader.replace("Bearer ", "");
        } else {
            return null;
        }
    }
}
