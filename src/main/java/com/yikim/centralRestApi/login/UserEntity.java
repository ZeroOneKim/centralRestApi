package com.yikim.centralRestApi.login;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import org.hibernate.annotations.CurrentTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "SY_USER_MT")
public class UserEntity {
    @Id
    @Column(name = "USER_ID")
    private String userId;

    @Column(name = "EMAIL")
    private String email;
    @Column(name = "ROLE")
    private String role;
    @Column(name = "PASSWORD")
    private String password;
    @Column(name = "CREATED_AT")
    private LocalDateTime createdAt;
    @Column(name = "ACCOUNT_STATUS")
    private String accountStatus;
    @Column(name = "FAILED_CNT")
    private int failedCnt;


    public String getUserId() {
        return userId;
    }
    public void setUser_id(String userId) {
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
