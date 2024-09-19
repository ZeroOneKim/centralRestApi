package com.yikim.centralRestApi.utils.database;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Database Connect 테스트.
 */
@Repository
public interface DataRepository extends JpaRepository<DataEntity, String> {
    DataEntity findByName(String name);
}