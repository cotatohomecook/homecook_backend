package com.cotato.homecook.config.security;

import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@NoArgsConstructor
@Component
public class JwtFilter extends OncePerRequestFilter {
    @Value("${jwt.secret}")
    private String jwtSecretKey;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String accessToken = JwtUtils.resolveAccessToken(request);
        if (JwtUtils.validateToken(accessToken, jwtSecretKey)) {
            setAuthentication(accessToken);
        }

        filterChain.doFilter(request, response);
    }

    private void setAuthentication(String accessToken) {
        String email = JwtUtils.getEmailFromToken(accessToken, jwtSecretKey);
        String role = JwtUtils.getRoleFromToken(accessToken, jwtSecretKey);
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(email, "", List.of(new SimpleGrantedAuthority(role)));
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        String path = request.getRequestURI();
        return path.startsWith("/api/auth");
    }
}
