package com.yikim.centralRestApi.storage;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface StorageRepository extends ReactiveCrudRepository<StorageUsageEntity, String> {

    Flux<StorageUsageEntity> findByUserId(String userId);
}
