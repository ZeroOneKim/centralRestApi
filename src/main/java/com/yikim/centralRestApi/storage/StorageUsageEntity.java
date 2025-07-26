package com.yikim.centralRestApi.storage;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;

@Table("STORAGE_USAGE_MT")
public class StorageUsageEntity {
    @Id
    @Column("USER_ID")
    private String userId;

    @Column("STORAGE_USE_MB")
    private Integer storageUseMb;

    @Column("MAX_STORAGE_MB")
    private Integer maxStorageMb;

    @Column("UPD_DT")
    private LocalDateTime updDt;

    @Column("RGST_DT")
    private LocalDateTime rgstDt;

    public String getUserId() {
        return userId;
    }
    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Integer getStorageUseMb() {
        return storageUseMb;
    }
    public void setStorageUseMb(Integer storageUseMb) {
        this.storageUseMb = storageUseMb;
    }

    public Integer getMaxStorageMb() {
        return maxStorageMb;
    }
    public void setMaxStorageMb(Integer maxStorageMb) {
        this.maxStorageMb = maxStorageMb;
    }

    public LocalDateTime getUpdDt() {
        return updDt;
    }
    public void setUpdDt(LocalDateTime updDt) {
        this.updDt = updDt;
    }

    public LocalDateTime getRgstDt() {
        return rgstDt;
    }
    public void setRgstDt(LocalDateTime rgstDt) {
        this.rgstDt = rgstDt;
    }
}
