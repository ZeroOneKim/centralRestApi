package com.yikim.centralRestApi.login;

import com.yikim.centralRestApi.utils.security.SecurityInfo;
import com.yikim.centralRestApi.utils.security.jwt.JwtTokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
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
    @Autowired LoginUtilService loginUtilService;
    @Autowired
    SecurityInfo securityInfo;

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

    /**
     * 회원가입 처리 메서드(운영자만 사용)
     *
     * @param userEntity : 사용자 정보객체
     * @param securityForSignUpPassCode : api 추가 패스워드
     * @return HTTP body
     */
    @PostMapping("/api/auth/secret/signup")
    public ResponseEntity<?> signUp(@RequestBody UserEntity userEntity, String securityForSignUpPassCode) {
        if(securityForSignUpPassCode != securityInfo.getSignUpSecureKeyVal()) {
            //TODO : GET IP INFO
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("부적합한 접근.");
        }
        if(userRepository.findByUserId(userEntity.getUserId()) != null
                || userRepository.findByUserId(userEntity.getUserId()).equals(userEntity.getUserId())) {
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
