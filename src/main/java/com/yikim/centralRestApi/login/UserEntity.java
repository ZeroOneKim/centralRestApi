package com.yikim.centralRestApi.login;


import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;

@Table("SY_USER_MT")
public class UserEntity {
    @Column("USER_ID")
    private String userId;
    @Column("EMAIL")
    private String email;
    @Column("USER_NM")
    private String userNm;
    @Column("ROLE")
    private String role;
    @Column("PASSWORD")
    private String password;
    @Column("RGST_DT")
    private LocalDateTime createdAt;
    @Column("ACCOUNT_STATUS")
    private String accountStatus;
    @Column("FAILED_CNT")
    private int failedCnt;


    public String getUserId() {
        return userId;
    }
    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    public String getUserNm() { return userNm;}
    public void setUserNm(String userNm) { this.userNm = userNm; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) {this.createdAt = createdAt;}

    public String getRole() {
        return role;
    }
    public void setRole(String role) {
        this.role = role;
    }

    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }

    public LocalDateTime getCreateAt() {
        return createdAt;
    }
    public void setCreateAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public String getAccountStatus() {
        return accountStatus;
    }
    public void setAccountStatus(String accountStatus) {
        this.accountStatus = accountStatus;
    }

    public int getFailedCnt() {
        return failedCnt;
    }
    public void setFailedCnt(int failedCnt) {
        this.failedCnt = failedCnt;
    }
}
