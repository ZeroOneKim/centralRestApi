package com.yikim.centralRestApi.login;

import com.yikim.centralRestApi.utils.security.jwt.JwtTokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

/**
 * 로그인 인증처리 유효성 확인 후 jwt 토큰을 return 한다.
 *
 * @Version : 0.9
 * @Since : 2024-10-07
 * @Author : 김영일
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
    public ResponseEntity<?> login(@RequestBody UserEntity userEntity) {
        try{
            UserEntity userObject = userRepository.findByUserId(userEntity.getUserId());
            if(userObject == null) return ResponseEntity.status(HttpStatus.NOT_FOUND).body("사용자가 존재하지 않음.");

            return Optional.ofNullable(userEntity)
                    .filter(userE -> userE.getPassword().equals(userObject.getPassword())) //TODO Password Encoding
                    .map(userE -> {
                        String jwtToken = jwtTokenUtils.makeToken(userObject.getUserId());

                        System.out.println("token    : " + jwtToken);   //TODO DEL
                        System.out.println("jwtToken : " + ResponseEntity.ok().body(jwtToken));  //TODO DEL

                        return ResponseEntity.ok().body(jwtToken);
                    })
                    .orElseGet(() -> ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("잘못된 인증"));

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("ERROR");
        }
    }


}
