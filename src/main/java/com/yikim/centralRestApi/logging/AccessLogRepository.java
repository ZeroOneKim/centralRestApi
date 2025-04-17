package com.yikim.centralRestApi.logging;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccessLogRepository extends ReactiveCrudRepository<AccessLogEntity, String> {

}
