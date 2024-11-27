package com.yikim.centralRestApi.utils.security.jwt;


import com.yikim.centralRestApi.utils.security.SecurityInfo;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

/**
 * JWT 토큰을 생성등의 유틸 클래스
 *
 * @Version : 9.1
 * @Since   : 2024-11-08
 * @Author  : 김영일
 */
@Service
public class JwtTokenUtils {
    @Autowired
    private SecurityInfo securityInfo;

    /**
     * JWT 토근 생성 메서드
     *
     * @param user : 사용자 정보
     * @return jwt 토큰
     */
    public String makeToken(String user) {
        return Jwts.builder()
                .setIssuedAt(new Date()) // 토큰 발행시간
                .setExpiration(new Date(System.currentTimeMillis() + (1000*3600)*12 )) //유효시간
                .setSubject(user) //정보 설정
                .signWith(SignatureAlgorithm.HS256, securityInfo.getRateOfOneSecretKeyValue())
                .compact();
    }

    /**
     * JWT 토큰에서 사용자 추출하는 메서드.
     * ※주의 : 없을 경우에는 NULL을 반환함. NPE 방어코드 유의
     *
     * @param token : JWT
     * @return User Information
     */
    public String extractUser(String token) {
        try {
            Claims claims = Jwts.parser()
                    .setSigningKey(securityInfo.getRateOfOneSecretKeyValue()).build()
                    .parseClaimsJws(token)
                    .getBody();
            return claims.getSubject();
        } catch (JwtException e) {
            return null;
        }
    }
    public boolean isValidJWT(String token) {
        try {
            extractUser(token);

            return true;
        } catch (JwtException e) {
            return false;
        }
    }
    /**
     * JWT 응답시, 헤더의 유효성 체크
     * @param header Bearer <jwt ~> 형태
     * @return boolean
     */
    public boolean headerIsValid(String header) {
        return Optional.ofNullable(header)
                .filter(header_ -> header_.startsWith("Bearer "))
                .isPresent();
    }
}
