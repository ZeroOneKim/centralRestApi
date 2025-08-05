package com.yikim.centralRestApi.storage;

import org.springframework.data.r2dbc.repository.Modifying;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface StorageInfoRepository extends ReactiveCrudRepository<StorageInfoEntity, String> {

    Flux<StorageInfoEntity> findByUserId(String userId);

    @Query("SELECT * FROM STORAGE_FILE_INFO WHERE USER_ID = :userId ORDER BY RGST_DT DESC")
    Flux<StorageInfoEntity> findByUserIdOrderByRgstDtDesc(@Param("userId") String userId);

    @Modifying
    @Query("DELETE FROM STORAGE_FILE_INFO WHERE USER_ID = :userId AND FILE_NAME = :fileName")
    Mono<Void> deleteByIdAndFileName(@Param("userId") String userId, @Param("fileName") String fileName);
}
