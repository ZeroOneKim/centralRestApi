package com.yikim.centralRestApi.login;


import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;

import java.time.LocalDateTime;

public class UserEntity {
    @Id
    @Column("USER_ID")
    private String userId;
    private String email;
    private String role;
    private String password;
    @Column("rgst_dt")
    private LocalDateTime createdAt;
    private String accountStatus;
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
