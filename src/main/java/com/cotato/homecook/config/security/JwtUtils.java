package com.cotato.homecook.config.security;

import com.cotato.homecook.config.auth.PrincipalDetails;
import com.cotato.homecook.config.auth.PrincipalDetailsService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Base64;
import java.util.Date;

@RequiredArgsConstructor
@Component
public class JwtUtils {
    @Value("${jwt.secret}")
    private String jwtSecretKey;
    private final long tokenValidTime = 30 * 60 * 1000L;
    private final PrincipalDetailsService principalDetailsService;

    @PostConstruct
    protected void init() {
        jwtSecretKey = Base64.getEncoder().encodeToString(jwtSecretKey.getBytes());
    }

    public String createToken(String email, String role, String username) {
        Claims claims = Jwts.claims();
        claims.put("email", email);
        claims.put("username", username);
        claims.put("role", role);
        Date now = new Date();
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + tokenValidTime))
                .signWith(SignatureAlgorithm.HS256, jwtSecretKey)
                .compact();
    }

}
