package com.yikim.centralRestApi.utils.security;

import org.apache.catalina.filters.CorsFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

/**
 * Spring Security 의 설정 클래스
 *
 * 1.CSRF 비활성화.
 * 2.jwt 사용
 * 3.CORS 를 처리하기 위한 CorsFilter 사용
 *
 * @Version : 0.9
 * @Since   : 2024-09-23
 * @Author  : 김영일
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Autowired
    private CorsFilter corsFilter;

    @Bean
    protected SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .csrf((value) -> value.disable())
                .authorizeRequests(req -> req
                            .requestMatchers("/login").permitAll()
                            .anyRequest().authenticated()
                            .and())
                .sessionManagement((session) ->
                            session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        return httpSecurity.build();
    }

    //TODO > User DB설계

    //TODO Password Encoding
}
