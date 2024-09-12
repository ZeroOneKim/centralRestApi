package com.yikim.centralRestApi.utils.database;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * DB 연결 테스트 목적 컨트롤러
 */
@RestController
public class DatabaseConnenctController {

    @GetMapping
    public String connectDBInfo() {
        return "Testing...";
    }
}
