package com.yikim.centralRestApi.utils.security;

import com.yikim.centralRestApi.utils.security.jwt.JwtAuthFilter;

import com.yikim.centralRestApi.utils.security.jwt.JwtTokenUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsWebFilter;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;

/**
 * Spring WebFluxSecurity 의 설정 클래스
 *
 * 1.CSRF 비활성화.
 * 2.jwt 사용
 * 3.CORS 를 처리하기 위한 CorsFilter 사용
 *
 * @Version : 0.9
 * @Since   : 2024-09-23
 * @Author  : 김영일
 *
 * @Modified : 2024-11-11 - WebFlux 버전으로 변경
 */
@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {
    private JwtAuthFilter jwtAuthFilter;

    public SecurityConfig(JwtTokenUtils jwtTokenUtils) {
        this.jwtAuthFilter = new JwtAuthFilter(jwtTokenUtils); // JwtAuthFilter 생성
    }

    /**
     * 기본적으로 WebFlux 보안 설정을 위함.
     * 경로 인증 / jwt 인증 처리
     *
     * 1. CSRF 보호 비활성화 (REST API 특성 상 비활성화)  왜??
     * 2. CORS 설정 기본값으로 설정
     * 3. 미인증 접근허용 경로 설정
     * 4. jwt 필터링을 이용하여 jwt 검증처리.
     *
     * @param httpSecurity : 보안 설정을 위한 HTTP(req/res) 객체
     * @return 필터체인 객체
     */
    @Bean
    protected SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity httpSecurity) {
        return httpSecurity
                .csrf((value) -> value.disable())
                .authorizeExchange(exchanges -> exchanges
                        .pathMatchers(HttpMethod.OPTIONS).permitAll()
                        .pathMatchers("/login", "/api/auth/login-process", "/data/connect").permitAll()
                        .anyExchange().authenticated()
                )
                .addFilterBefore(jwtAuthFilter, SecurityWebFiltersOrder.AUTHENTICATION)
                .build();
    }

    /**
     * CORS 설정
     *
     * @return CorsWebFilter : CORS 설정 필터
     */
    @Bean
    public CorsWebFilter corsWebFilter() {                 //TODO CHANGE ADDRESS
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        config.addAllowedOrigin("https://localhost:3000");
        config.addAllowedOrigin("https://192.168.0.4");
        config.addAllowedOrigin("https://www.yi97-cloud.com");
        config.addAllowedOrigin("https://yi97-cloud.com");


        config.addAllowedHeader("*");
        config.addAllowedMethod("*");

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);

        return new CorsWebFilter(request -> config);
    }

    /**
     * 패스워드 인코더 설정
     * BCrypt 사용 -> 솔트값 기본값으로
     * @return PasswordEncoder : 인코더
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
