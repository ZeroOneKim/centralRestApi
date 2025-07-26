package com.yikim.centralRestApi.storage;

import com.yikim.centralRestApi.utils.security.jwt.JwtTokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * 스토리지 관련 유틸 및 서비스
 *
 * @version : 0.93
 * @author  : 김영일
 * @since   : 2025-07-26
 */
@Service
public class StorageService {
    @Autowired JwtTokenUtils jwtTokenUtils;
    @Autowired StorageRepository storageRepository;

    /**
     * 전달 받은 파일에 대한 용량 초과 여부 확인
     *
     * @param filePart 파일
     * @param jwtToken jwt 토큰
     * @return Boolean(True or False)
     */
    public Mono<Boolean> storageIsAvailable(FilePart filePart, String jwtToken) {
        AtomicInteger size = new AtomicInteger(0);

        return filePart.content()
                .doOnNext(dataBuffer -> {
                    int chunkSize = dataBuffer.readableByteCount();
                    size.addAndGet(chunkSize);
                    DataBufferUtils.release(dataBuffer); // 사용 후 즉시 메모리 해제
                })
                .then(
                    storageRepository.findByUserId(jwtTokenUtils.extractUser(jwtToken))
                    .single()
                    .map(storageEntity -> {
                        int maxStorage = storageEntity.getMaxStorageMb();
                        int usedStorage = storageEntity.getStorageUseMb();
                        int newSizeMB = (size.get()/1024/1024 == 0) ? 0 : 1;

                        if(usedStorage+newSizeMB > maxStorage) return false;
                        return true;
                    }).switchIfEmpty(Mono.error(new Exception("Not found user")))
                );
    }

}


