package com.yikim.centralRestApi.utils.database.test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

/**
 * DB 연결 테스트 목적 컨트롤러
 */
@RestController
public class DatabaseConnectController {
    @Autowired
    private DataRepository dataRepository;

    @GetMapping("/data/connect")
    public String connectDBInfo() {
        Optional<DataEntity> dataEntity = dataRepository.findById("yikim");
        System.out.println(dataEntity);


        return "Testing...";
    }

}
