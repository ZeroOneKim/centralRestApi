package com.yikim.centralRestApi.login;

import com.yikim.centralRestApi.utils.security.jwt.JwtTokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.server.Cookie;
import org.springframework.http.*;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.Optional;

/**
 * 로그인 인증처리
 *
 * @Version : 0.9
 * @Since : 2024-10-07
 * @Author : 김영일
 *
 * @Modified : 2024-11-20 - WebFlux 버전으로 변경
 */
@RestController
public class LoginController {
    @Autowired UserRepository userRepository;
    @Autowired PasswordEncoder passwordEncoder;
    @Autowired JwtTokenUtils jwtTokenUtils;

    /**
     * login 인증 프로세스
     *
     * @param userEntity 사용자 정보 객체
     * @return jwt 토큰
     */
    @PostMapping("/api/auth/login-process")
    public Mono<ResponseEntity<String>> login(@RequestBody UserEntity userEntity) {
        return userRepository.findByUserId(userEntity.getUserId())
                .flatMap(userObject -> {
                    // 패스워드 일치 여부 확인
                    if (!passwordEncoder.matches(userEntity.getPassword(), userObject.getPassword())) {
                        return Mono.just(ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("잘못된 인증"));
                    }

                    // JWT 토큰 생성
                    String jwtToken = jwtTokenUtils.makeToken(userObject.getUserId());
                    System.out.println(jwtToken);
                    return Mono.just(ResponseEntity.ok().body(jwtToken));

                })
                .switchIfEmpty(Mono.just(ResponseEntity.status(HttpStatus.NOT_FOUND).body("Not Found User...")))
                .onErrorResume(e -> {  //ERR
                    e.printStackTrace();
                    return Mono.just(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("ERROR"));
                });
    }

    @GetMapping("/api/isAuthenticated/jwt")
    public Mono<ResponseEntity<String>> isAuthenticatedOfCookieInJWT(@RequestHeader(HttpHeaders.AUTHORIZATION) String authorization) {


        return Mono.just(ResponseEntity.ok("Authenticated"));
    }


    /**
     * 회원가입 처리 메서드(운영자만 사용)
     *
     * @param userEntity : 사용자 정보객체
     * @return HTTP body
     */
    @PostMapping("/api/auth/secret/signup")
    public ResponseEntity<?> signUp(@RequestBody UserEntity userEntity) {
        //TODO To admin
        Mono<UserEntity> existingUser = userRepository.findByUserId(userEntity.getUserId());

        if(true) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("InValid UserId");
        }
        //나만 쓸껀데 검증이 필요할까.?
        try {
            userEntity.setPassword(passwordEncoder.encode(userEntity.getPassword()));
            userEntity.setCreateAt(LocalDateTime.now());

            userRepository.save(userEntity);
            return ResponseEntity.ok().body("OK.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("InValid Value");
        }
    }
}
