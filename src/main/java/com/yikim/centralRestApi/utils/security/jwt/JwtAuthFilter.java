package com.yikim.centralRestApi.utils.security.jwt;

import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

import java.util.ArrayList;

/**
 * JWT 토큰에 대한 인증 처리 클래스(유효성 검사 -> 인증처리 여부 확인)
 * 매 요청마다 실행. {@code Authorization} 헤더에서 JWT 토큰 추출 후, 사용자 인증 수행
 *
 * @Version : 0.91
 * @Since   : 2024-10-08
 * @Author  : KRM)김영일
 *
 * @Modified : 2024-11-11 - WebFlux 버전으로 변경
 */
public class JwtAuthFilter implements WebFilter {
    private JwtTokenUtils jwtTokenUtils;

    public JwtAuthFilter(JwtTokenUtils jwtTokenUtils) {
        this.jwtTokenUtils = jwtTokenUtils;
    }

    /**
     * JWT 토큰 추출, 인증처리 메서드
     * HTTP 요청의 헤더에 jwt 토큰을 추출하여, 유효성 검증 후 컨텍스트에 설정
     * Authorization : Bearer [jwt token]
     *
     * @param exchange : HTTP 요청 및 응답 정보를 포함 객체
     *                   -> ServerWebExchange 는 WebFlux 에서 서버 요청과 응답을 추상화한 객체임. (Req + Res) -> 논블로킹 목적
     * @param chain    : 필터로 요청을 전달하는 객체
     * @return 인증 정보 설정
     */
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        String header = exchange.getRequest().getHeaders().getFirst("Authorization");

        if(header != null && header.startsWith("Bearer ")) {
            String jwtToken = header.substring(7);
            try {
                String username = jwtTokenUtils.extractUser(jwtToken);

                if (username != null) {
                    System.out.println("JWT Token is valid, username: " + username);  // 로그 추가
                    UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(username, null, new ArrayList<>()); //TODO Auth 관리

                    return chain.filter(exchange)
                            .contextWrite(ReactiveSecurityContextHolder.withAuthentication(auth)); // Context 설정
                }
            } catch (Exception e) {
                exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
                return exchange.getResponse().setComplete();
            }
        }
        return chain.filter(exchange);
    }
}
