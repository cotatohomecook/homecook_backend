package com.cotato.homecook.config.security;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.filter.CorsFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    @Value("${jwt.secret}")
    private String jwtSecretKey;
    private final CorsConfig corsConfig;
    private final CorsFilter corsFilter;

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    protected SecurityFilterChain configure(HttpSecurity http) throws Exception {
        // 시큐리티 필터가 커스텀 필터보다 먼저 작동함
//        http.addFilterBefore(new MyFilter3(), SecurityContextHolderFilter.class);
        http.csrf().disable();
        http
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .formLogin().disable()
                .httpBasic().disable()
                .apply(new MyCustomDsl()) // AuthenticationManager를 넘겨야됨
                .and()
                .authorizeRequests()
                .antMatchers("/api/auth/**")
                .permitAll()
                .antMatchers("/api/**")
////                        .permitAll()
                .access("hasRole('ROLE_CUSTOMER') or hasRole('ROLE_ADMIN') or hasRole('ROLE_SELLER')")
//                        .hasAnyRole()
//                        .antMatchers("/api/v1/user/**")
//                        .access("hasRole('ROLE_USER') or hasRole('ROLE_MANAGER') or hasRole('ROLE_ADMIN')")
//                        .antMatchers("/api/v1/manager/**")
//                        .access("hasRole('ROLE_MANAGER') or hasRole('ROLE_ADMIN')")
//                        .antMatchers("/api/v1/admin/**")
//                        .access("hasRole('ROLE_ADMIN')")
                .anyRequest().permitAll()
                .and()
                .addFilterBefore(new JwtFilter(jwtSecretKey), UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(new JwtExceptionFilter(), JwtFilter.class);


        return http.build();
    }

    public class MyCustomDsl extends AbstractHttpConfigurer<MyCustomDsl, HttpSecurity> {
        @Override
        public void configure(HttpSecurity http) throws Exception {
            AuthenticationManager authenticationManager = http.getSharedObject(AuthenticationManager.class);

            http
                    .addFilter(corsConfig.corsFilter());
        }
    }
}
