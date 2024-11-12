package com.yikim.centralRestApi.utils.database.test;

import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

/**
 * Database Connect 테스트.
 */
@Repository
public interface DataRepository extends ReactiveCrudRepository<DataEntity, String> {
    @Query("SELECT * FROM TEST_CONNECT_TAB WHERE NAME_ = :name")
    Flux<DataEntity> findByName(@Param("name") String name);
}