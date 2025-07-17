package com.yikim.centralRestApi.storage;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
public class StorageController {
    @PostMapping("/api/storage/doUpload")
    public Mono<ResponseEntity<?>> doUploadFileToStorage() {



        return Mono.just(ResponseEntity.ok(""));
    }

}
