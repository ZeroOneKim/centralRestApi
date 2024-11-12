package com.yikim.centralRestApi.utils.database.test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Optional;

/**
 * DB 연결 테스트 목적 컨트롤러
 */
@RestController
public class DatabaseConnectController {
    @Autowired
    private DataRepository dataRepository;

    @GetMapping("/data/connect")
    public Flux<DataEntity> connectDBInfo() {

        Flux<DataEntity> dataEntity = dataRepository.findByName("yikim");

        return dataEntity
                .doOnNext(data -> System.out.println("DataEntity found: " + data))
                .doOnError(error -> System.out.println("Error occurred: " + error.getMessage()));
    }

}
