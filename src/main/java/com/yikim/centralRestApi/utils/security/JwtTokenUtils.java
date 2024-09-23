package com.yikim.centralRestApi.utils.security;


import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

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
        return Jwts.parser()
                .setSigningKey(securityInfo.getRateOfOneSecretKeyValue())
                .build()
                .parseClaimsJwt(token)
                .getBody()
                .getSubject();
    }
}
