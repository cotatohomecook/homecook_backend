package com.cotato.homecook.config.security;

import com.cotato.homecook.config.auth.PrincipalDetails;
import com.cotato.homecook.config.auth.PrincipalDetailsService;
import com.cotato.homecook.exception.AppException;
import com.cotato.homecook.exception.ErrorCode;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
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

    public static boolean validateToken(String token, String jwtSecretKey){
        try{
            Claims claims = Jwts.parser().setSigningKey(jwtSecretKey).parseClaimsJws(token).getBody();
            return true;
        } catch (SignatureException e){
            throw new AppException(ErrorCode.WRONG_JWT_TOKEN);
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

}
