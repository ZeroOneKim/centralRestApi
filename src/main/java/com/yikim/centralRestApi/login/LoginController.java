package com.yikim.centralRestApi.login;

import com.yikim.centralRestApi.utils.security.JwtTokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {
    @Autowired UserRepository userRepository;
    @Autowired PasswordEncoder passwordEncoder;
    @Autowired JwtTokenUtils jwtTokenUtils;

    @PostMapping("/api/auth/login-process")
    public ResponseEntity<?> login(@RequestBody UserEntity userEntity) {
        try{
            UserEntity userObject = userRepository.findByUserId(userEntity.getUserId());

            System.out.println("1 : " + userEntity.getUserId());
            System.out.println("2 : " + userObject.getUserId());



        } catch (Exception e) {

        }
        return null;
    }
}
