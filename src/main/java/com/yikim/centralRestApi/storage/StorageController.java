package com.yikim.centralRestApi.storage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * 스토리지 관련 컨트롤러
 */
@RestController
public class StorageController {
    @Autowired StorageService storageService;

    @PostMapping("/api/storage/doUpload")
    public Mono<ResponseEntity<String>> doUploadFileToStorage(@RequestPart("files") Flux<FilePart> upLoadFileData
                                                            ,@CookieValue("yi-jwt") String jwtToken) {
        return upLoadFileData.flatMap(filePart -> {
            //Entity 객체
            return storageService.storageIsAvailable(filePart, jwtToken)
                    .map(isAvailable -> {
                        if(!isAvailable) {
                            throw new RuntimeException("허용된 용량 초과");
                        }
                        return filePart;
                    });
        })
        .collectList()
        .flatMap(validFile ->
                storageService.uploadFile(validFile, jwtToken)
                .then(storageService.modifyUsedStorageAmount(validFile.get(0).filename(), jwtToken)))
        .then(Mono.just(ResponseEntity.ok("Success")))
        .onErrorResume(ex -> {
            return Mono.just(ResponseEntity.badRequest().body(ex.getMessage())); //TODO onError
        });
    }

}
