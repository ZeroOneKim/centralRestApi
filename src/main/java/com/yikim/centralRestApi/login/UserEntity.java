package com.yikim.centralRestApi.login;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.time.LocalDateTime;

@Entity
@Table(name = "SY_USER_MT")
public class UserEntity {
    @Id
    private String user_id;

    private String email;
    private String role;
    private String password;

    private LocalDateTime create_at;
    private String account_status;
    private int failed_cnt;


    public String getUser_id() {
        return user_id;
    }
    public void setUser_id(String user_id) {
        this.user_id = user_id;
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

    public LocalDateTime getCreate_at() {
        return create_at;
    }
    public void setCreate_at(LocalDateTime create_at) {
        this.create_at = create_at;
    }

    public String getAccount_status() {
        return account_status;
    }
    public void setAccount_status(String account_status) {
        this.account_status = account_status;
    }

    public int getFailed_cnt() {
        return failed_cnt;
    }
    public void setFailed_cnt(int failed_cnt) {
        this.failed_cnt = failed_cnt;
    }
}
