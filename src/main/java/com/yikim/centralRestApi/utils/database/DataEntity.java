package com.yikim.centralRestApi.utils.database;

import jakarta.persistence.*;

/**
 * Database Connect 테스트.
 */
@Entity
@Table(name = "TEST_CONNECT_TAB")
public class DataEntity {

    @Id
    @Column(name = "NAME_")
    private String name;

    @Column(name = "CONNECT")
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
