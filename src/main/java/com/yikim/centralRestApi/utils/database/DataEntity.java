package com.yikim.centralRestApi.utils.database;

import jakarta.persistence.*;

@Entity
@Table(name = "TEST_CONNECT_TAB")
public class DataEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "NAME_")
    private String name;

    @Column(name = "CONNECT")
    private boolean connect;
}
