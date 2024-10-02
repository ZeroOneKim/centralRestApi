package com.yikim.centralRestApi.login;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {

    @PostMapping("/api/auth/login-process")
    public ResponseEntity<?> login(@RequestBody UserEntity userEntity) {
        try{

        } catch () {

        }
    }
}
