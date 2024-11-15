package com.yikim.centralRestApi.login;

import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface UserRepository extends ReactiveCrudRepository<UserEntity, String> {

    @Query("SELECT * FROM SY_USER_MT WHERE USER_ID = :userId")
    Mono<UserEntity> findByUserId(String userId);
}
