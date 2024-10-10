package com.yikim.centralRestApi.utils.security.jwt;


import com.yikim.centralRestApi.utils.security.SecurityInfo;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
public class JwtTokenUtils {
    @Autowired
    private SecurityInfo securityInfo;

    public String makeToken(String user) {
        return Jwts.builder()
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + (1000*3600)*12 ))
                .setSubject(user)
                .signWith(SignatureAlgorithm.HS256, securityInfo.getRateOfOneSecretKeyValue())
                .compact();
    }

    public String extractUser(String token) {
        try {
            Claims claims = Jwts.parser()
                    .setSigningKey(securityInfo.getRateOfOneSecretKeyValue()).build()
                    .parseClaimsJws(token)
                    .getBody();
            return claims.getSubject(); // 사용자의 username이 Subject에 설정되었다고 가정
        } catch (Exception e) {
            e.printStackTrace();
            return null; // 사용자 정보를 추출하지 못하면 null 반환
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
