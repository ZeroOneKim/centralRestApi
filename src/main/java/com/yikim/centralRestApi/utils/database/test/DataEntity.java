package com.yikim.centralRestApi.utils.database.test;


import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

/**
 * Database Connect 테스트.
 */
@Table("TEST_CONNECT_TAB")
public class DataEntity {
    @Id
    @Column("NAME_")
    private String name;
    private boolean connect;

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public boolean isConnect() {
        return connect;
    }
    public void setConnect(boolean connect) {
        this.connect = connect;
    }
}
