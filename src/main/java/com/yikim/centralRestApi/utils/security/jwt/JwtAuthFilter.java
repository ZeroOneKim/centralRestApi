package com.yikim.centralRestApi.utils.security.jwt;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.ArrayList;

/**
 * JWT 토큰에 대한 인증 처리 클래스(유효성 검사 -> 인증처리 여부 확인)
 * 매 요청마다 실행. {@code Authorization} 헤더에서 JWT 토큰 추출 후, 사용자 인증 수행
 *
 * @Version : 0.9
 * @Since   : 2024-10-08
 * @Author  : KRM)김영일
 */
public class JwtAuthFilter extends OncePerRequestFilter {
    private JwtTokenUtils jwtTokenUtils;

    public JwtAuthFilter(JwtTokenUtils jwtTokenUtils) {
        this.jwtTokenUtils = jwtTokenUtils;
    }

    /**
     * JWT 토큰 추출, 인증처리 메서드
     * Authorization : Bearer [jwt token]
     *
     * @param request 클라이언트 측 HTTP 요청
     * @param response HTTP 응답
     * @param filterChain 구현 한 필터체인 객체
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response
                                                              , FilterChain filterChain) throws ServletException, IOException {
        String header = request.getHeader("Authorization");
        if(jwtTokenUtils.headerIsValid(header)) {
            String jwtToken = header.substring(7);
            try {
                System.out.println("Test Token Check : " + jwtToken);
                String username = jwtTokenUtils.extractUser(jwtToken);

                if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                    UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(username, null, new ArrayList<>());
                    SecurityContextHolder.getContext().setAuthentication(auth);
                }
            } catch (Exception e) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            }

        }

        filterChain.doFilter(request, response);
    }
}
