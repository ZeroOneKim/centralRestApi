package com.yikim.centralRestApi.storage;

import com.yikim.centralRestApi.utils.security.SecurityInfo;
import com.yikim.centralRestApi.utils.security.jwt.JwtTokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;

/**
 * 스토리지 관련 유틸 및 서비스 -> DB Part / Util Part 나뉨.
 *
 * @version : 0.93
 * @author  : 김영일
 * @since   : 2025-07-26
 */
@Service
public class StorageService {
    @Autowired JwtTokenUtils jwtTokenUtils;
    @Autowired StorageInfoRepository storageInfoRepository;
    @Autowired StorageRepository storageRepository;
    @Autowired SecurityInfo securityInfo;

    public Long fileSize;
    /**
     * 현재 사용되고 있는 용량에 정보에 대한 업데이트
     * @param jwtToken
     * @return void(저장처리)
     */
    public Mono<Void> modifyUsedStorageAmount(String fileName, String jwtToken) {
        String userId     = jwtTokenUtils.extractUser(jwtToken);
        String targetPath = securityInfo.getBaseStorageDir();

        return nowFolderSize(targetPath) // userId를 nowFolderSize로 전달하도록 변경 필요 (아래 nowFolderSize 리뷰 참고)
                .flatMap(currentFolderSize -> storageRepository.findByUserId(userId)
                     .single()
                     .flatMap(entity -> {
                         entity.setStorageUseMb(currentFolderSize/1024/1024);
                         entity.setUpdDt(LocalDateTime.now());

                         return storageRepository.save(entity);
                     }).thenReturn(currentFolderSize))

                .flatMap(saveStoreInfo -> {
                    StorageInfoEntity newEntity = new StorageInfoEntity();
                    newEntity.setUserId(userId);
                    newEntity.setFileName(fileName);
                    newEntity.setFileSize(fileSize);
                    newEntity.setRgstDt(LocalDateTime.now());

                    return storageInfoRepository.deleteByIdAndFileName(userId, fileName)
                            .onErrorResume(e -> Mono.empty())
                            .then(storageInfoRepository.save(newEntity))
                            .then();
                })
                .doOnError(e -> {
                    // 폴더 크기 계산이나 DB 업데이트 중 에러 발생 시 처리
                    System.err.println("사용량 수정 중 오류 발생: " + e.getMessage());
                })
                .then(); // 최종적으로 Mono<Void>를 반환하도록 합니다.
    }

    //TODO GET FILE INFO and view




    /************************************************* Util ***********************************************/
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
                        int newSizeMB = (size.get()/1024/1024 == 0) ? 1 : size.get()/1024/1024;

                        if(usedStorage+newSizeMB > maxStorage) return false;
                        fileSize = Long.valueOf(size.get());

                        return true;
                    }).switchIfEmpty(Mono.error(new Exception("Not found user")))
                );
    }

    //TODO review
    public Mono<Void> uploadFile(List<FilePart> file, String jwtToken) {
        String userId = jwtTokenUtils.extractUser(jwtToken);
        Path userDir = Paths.get(securityInfo.getBaseStorageDir(), userId);

        Mono<Void> ensureDir = Mono.fromRunnable(() -> {
            try {
                Files.createDirectories(userDir);
            } catch (IOException exception) {
                throw new RuntimeException("업로드 폴더 생성 실패하였음.");
            }
        }).subscribeOn(Schedulers.boundedElastic()).then();

        Flux<Void> saveFiles = Flux.fromIterable(file)
                .flatMap(filePart -> {
                    Path destination = userDir.resolve(filePart.filename());
                    return filePart.transferTo(destination);
                });

        return ensureDir.thenMany(saveFiles).then();
    }

    //TODO Review
    public Mono<Integer> nowFolderSize(String targetPath) {
        return Mono.fromCallable(() -> {
            Path folder = Paths.get(targetPath);
            final long totalBytes;

            try (Stream<Path> paths = Files.walk(folder)) {
                totalBytes = paths
                        .filter(Files::isRegularFile)
                        .mapToLong(path -> {
                            try {
                                return Files.size(path);
                            } catch (IOException e) {
                                return 0L;
                            }
                        })
                        .sum();
            }
            return (int) (totalBytes);
        }).subscribeOn(Schedulers.boundedElastic());
    }
}


